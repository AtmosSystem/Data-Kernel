(ns atmos-data-kernel.core
  (:require [korma.db :as sql]
            [korma.core :refer :all]))

;------------------------------
; BEGIN General functions
;------------------------------

(defn defpersistence [db-map]
  (let [db-map (clojure.core/update db-map
                                    :classname #(str "com.mysql.cj.jdbc.Driver" %))]
    (sql/mysql db-map)))

(defn init-persistence [db-definition] (sql/defdb atmos-users db-definition))

;------------------------------
; END General functions
;------------------------------


;------------------------------
; BEGIN CRUD functions
;------------------------------

(defn- fn-entity-symbol
  ([type entity no-asterisk]
   (symbol (apply str [(name type) "-" (name entity) (if-not no-asterisk "*")])))
  ([type entity]
   (fn-entity-symbol type entity false)))

(defmacro defadd-entity
  [entity args-fn add-persist-fn]
  (let [fn-name (fn-entity-symbol :add entity)]
    `(defn- ~fn-name
       ~args-fn
       (if-let [key-inserted# (~add-persist-fn (first ~args-fn))]
         (:generated_key key-inserted#)
         false))))

(defmacro defget-entity
  [entity args-fn get-persist-fn id-name]
  (let [fn-name (fn-entity-symbol :get entity)]
    `(defn- ~fn-name
       ~args-fn
       (~get-persist-fn {~id-name (first ~args-fn)}))))

(defmacro defget-identity-entity
  [entity args-fn get-persist-fn id-name]
  (let [fn-name (fn-entity-symbol :get entity)]
    `(defn- ~fn-name
       ~args-fn
       (first (~get-persist-fn {~id-name (first ~args-fn)})))))


(defmacro defget-all-entity
  [entity get-persist-base-fn]
  (let [fn-name (fn-entity-symbol :get-all entity true)]
    `(defn ~fn-name
       []
       (-> ~get-persist-base-fn
           select))))

(defmacro defupdate-entity
  [entity args-fn get-entity-fn update-persist-fn id-name]
  (let [fn-name (fn-entity-symbol :update entity)
        arg-fn (first args-fn)]
    `(defn- ~fn-name
       ~args-fn
       (if-let [exists# (~get-entity-fn (~id-name ~arg-fn))]
         (do
           (~update-persist-fn ~arg-fn)
           true)
         false))))

(defn remove-cond-fn
  [remove-entity-fn get-entity-fn cond data]
  (if-let [entity (get-entity-fn data)]
    (do
      (remove-entity-fn cond)
      true)
    false))

(defmacro defremove-entity
  [entity args-fn get-entity-fn remove-persist-fn id-name]
  (let [fn-name (fn-entity-symbol :remove entity)
        arg-fn (first args-fn)]
    `(defn- ~fn-name
       ~args-fn
       (remove-cond-fn ~remove-persist-fn ~get-entity-fn {~id-name ~arg-fn} ~arg-fn))))

;------------------------------
; END CRUD functions
;------------------------------
