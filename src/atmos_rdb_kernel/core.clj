(ns atmos-rdb-kernel.core
  (:require [korma.db :as sql]))

;------------------------------
; BEGIN General functions
;------------------------------

(defn defpersistence [db-map]
  (let [db-map (clojure.core/update db-map
                                    :classname #(str "com.mysql.cj.jdbc.Driver" %))]
    (sql/mysql db-map)))

(defn init-persistence [db-definition] (sql/defdb atmos-users db-definition))

;------------------------------
; END - General functions
;------------------------------

