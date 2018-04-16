(ns atmos-data-kernel.relational.core
  (:require [korma.db :as sql]))

(defn defpersistence [db-map]
  (let [db-map (clojure.core/update db-map
                                    :classname #(str "com.mysql.cj.jdbc.Driver" %))]
    (sql/mysql db-map)))

(defn init-persistence [db-definition] (sql/defdb atmos-users db-definition))
