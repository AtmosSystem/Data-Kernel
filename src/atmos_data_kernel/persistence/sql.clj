(ns atmos-data-kernel.persistence.sql
  (:require [atmos-data-kernel.persistence.core :refer :all]
            [korma.core :refer :all]))


;------------------------------
; BEGIN ADD functions
;------------------------------

(defmacro defadd-entity
  "Define add entity function"
  [entity]
  (let [fn-name (entity-fn-name :add entity)
        args '[data]]
    `(defn- ~fn-name
       ~args
       (if-let [key-inserted# (insert ~entity (values (first ~args)))]
         (:generated_key key-inserted#)
         false))))

;------------------------------
; END ADD functions
;------------------------------

;------------------------------
; BEGIN GET functions
;------------------------------

(defmacro defget-entity
  "Define get entity function using persist function to retrieve values"
  [entity persist-fn]
  (let [fn-name (entity-fn-name :get entity)
        id-name (:pk entity)
        args '[data]]
    `(defn- ~fn-name
       ~args
       (~persist-fn {~id-name (first ~args)}))))

(defmacro defget-identity-entity
  "Define get entity using persist function to retrieve the first one value"
  [entity get-persist-fn]
  (let [fn-name (entity-fn-name :get entity)
        id-name (:pk entity)
        args '[data]]
    `(defn- ~fn-name
       ~args
       (first (~get-persist-fn {~id-name (first ~args)})))))


(defmacro defget-all-entity
  "Define get all entity function using persist function to retrieve values"
  [entity get-persist-fn]
  (let [fn-name (entity-fn-name :get-all entity false)]
    `(defn ~fn-name
       []
       (~get-persist-fn))))


;------------------------------
; END GET functions
;------------------------------

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
