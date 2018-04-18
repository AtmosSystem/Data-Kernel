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
  ([entity select-fn args-fn]
   (let [fn-name (entity-fn-name :get entity)]
     `(defn- ~fn-name
        ~args-fn
        (apply ~select-fn ~args-fn))))
  ([entity select-fn]
   (defget-entity entity select-fn '[data])))

(defmacro defget-identity-entity
  "Define get entity using persist function to retrieve the first one value"
  ([entity select-fn args-fn]
   (let [fn-name (entity-fn-name :get-first entity)]
     `(defn- ~fn-name
        ~args-fn
        (first (apply ~select-fn ~args-fn)))))
  ([entity select-fn]
   (defget-identity-entity entity select-fn '[data])))


(defmacro defget-all-entity
  "Define get all entity function using persist function to retrieve values"
  [entity select-fn]
  (let [fn-name (entity-fn-name :get-all entity false)]
    `(defn ~fn-name
       []
       (~select-fn))))


;------------------------------
; END GET functions
;------------------------------

(defmacro defupdate-entity
  "Define an update entity function"
  ([entity key-id-name]
   (let [fn-name (entity-fn-name :update entity)
         get-first-fn (entity-fn-name :get-first entity)
         args '[data]]
     `(defn- ~fn-name
        ~args
        (if-let [exists# (apply ~get-first-fn ~args)]
          (do
            (update ~entity
                    (set-fields (first ~args))
                    (where {~key-id-name (~key-id-name (first ~args))}))
            true)
          false))))
  ([entity]
   (defupdate-entity entity (:pk entity))))

(defmacro defremove-entity
  "Define a remove entity function"
  ([entity key-id-name]
   (let [fn-name (entity-fn-name :remove entity)
         get-first-fn (entity-fn-name :get-first entity)
         args '[data]]
     `(defn- ~fn-name
        ~args
        (if-let [exists# (apply ~get-first-fn ~args)]
          (do
            (delete ~entity
                    (where {~key-id-name (~key-id-name (first ~args))}))
            true)
          false))))
  ([entity]
   (defremove-entity entity (:pk entity))))
