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
  (let [fn-name (entity-fn-name :get-first entity)
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
  "Define an update entity function"
  ([entity id-name]
   (let [fn-name (entity-fn-name :update entity)
         get-first-fn (entity-fn-name :get-first entity)
         args '[data]]
     `(defn- ~fn-name
        ~args
        (if-let [exists# (~get-first-fn (~id-name (first ~args)))]
          (do
            (update ~entity
                    (set-fields (first ~args))
                    (where {~id-name (~id-name (first ~args))}))
            true)
          false))))
  ([entity]
   (defupdate-entity entity (:pk entity))))

(defmacro defremove-entity
  "Define a remove entity function"
  ([entity id-name]
   (let [fn-name (entity-fn-name :remove entity)
         get-first-fn (entity-fn-name :get-first entity)
         args '[data]]
     `(defn- ~fn-name
        ~args
        (if-let [exists# (~get-first-fn (~id-name (first ~args)))]
          (do
            (delete ~entity
                    (where {~id-name (~id-name (first ~args))}))
            true)
          false))))
  ([entity]
   (defremove-entity entity (:pk entity))))
