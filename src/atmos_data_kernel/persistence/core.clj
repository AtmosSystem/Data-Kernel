(ns atmos-data-kernel.persistence.core
  (:require [atmos-data-kernel.core :refer [update-map]]
            [clojure.string :refer [lower-case]]
            [korma.db :refer [mysql]]))

(def ^:private persistence-types {:mysql [mysql
                                          "com.mysql.cj.jdbc.Driver"]})

(defn- persistence-dialect
  "Return korma persistence dialect"
  [persistence-type]
  (first (persistence-type persistence-types)))

(defn- db-class-name
  "Return persistence relational db class name"
  [persistence-type]
  (second (persistence-type persistence-types)))

(defn entity-fn-name
  "Return the entity function name"
  ([type entity asterisk]
   (let [type (lower-case (name type))
         entity (lower-case (name entity))]
     (symbol (apply str [type "-" entity (if asterisk "*")]))))
  ([type entity]
   (entity-fn-name type entity true)))


(defn defpersistence
  "Define persistence device map"
  [persistence-type persistence-data]
  (let [persistence-fn (persistence-dialect persistence-type)
        classname (db-class-name persistence-type)
        persistence-data (case persistence-type
                           :mysql (update-map persistence-data
                                              :classname
                                              #(str classname %)))]
    (apply persistence-fn [persistence-data])))

