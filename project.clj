(defproject atmos-system/atmos-data-kernel "1.0-SNAPSHOT"
  :description "Data core functionality of Atmos System"
  :url "https://github.com/AtmosSystem/Data-Kernel"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :profiles {:dev {:dependencies [[org.clojure/test.check "0.9.0"]]}}
  :repositories [["github" {:url      "https://maven.pkg.github.com/AtmosSystem/Data-Kernel"
                            :username :env/github_username
                            :password :env/github_token}]])
