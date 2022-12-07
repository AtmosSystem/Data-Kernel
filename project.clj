(defproject atmos-data-kernel "0.6.8"
  :description "Data core functionality of Atmos System"
  :url "https://github.com/AtmosSystem/Data-Kernel"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 ;persistence-deps
                 [korma "0.4.3"]
                 [mysql/mysql-connector-java "8.0.30"]]
  :repositories [["releases" {:url           "https://clojars.org/repo"
                              :username      :env/CLOJAR_USERNAME
                              :password      :env/CLOJAR_PASSWORD
                              :sign-releases false}]])
