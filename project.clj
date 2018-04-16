(defproject atmos-data-kernel "0.3.0-SNAPSHOT"
  :description "Relational database core functionality of Atmos System"
  :url "https://github.com/AtmosSystem/RDB-Kernel"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 ;persistence-deps
                 [korma "0.4.3"]
                 [mysql/mysql-connector-java "8.0.9"]]
  :repositories [["releases" {:url           "https://clojars.org/repo"
                              :username      :env/CLOJAR_USERNAME
                              :password      :env/CLOJAR_PASSWORD
                              :sign-releases false}]])
