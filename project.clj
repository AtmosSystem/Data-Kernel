(defproject org.clojars.atmos-system/atmos-data-kernel "1.0"
  :description "Data core functionality of Atmos System"
  :url "https://github.com/AtmosSystem/Data-Kernel"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :profiles {:dev {:dependencies [[org.clojure/test.check "0.9.0"]]}}
  :deploy-repositories [["clojars" {:sign-releases false
                                    :url           "https://repo.clojars.org/"
                                    :username      :env/clojars_username
                                    :password      :env/clojars_password}]])
