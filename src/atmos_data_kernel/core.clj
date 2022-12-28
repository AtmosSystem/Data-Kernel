(ns atmos-data-kernel.core
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as string]))

(defprotocol PDataDevice
  "This represent a data persistence device"
  (open [device]))

(defprotocol PDataDeviceConnection
  (repository [connection repository-name])
  (close [connection]))

(defprotocol PSimpleDataRepository
  "Represent a structure to make operations over the persistence device"
  (get-all [device] [device options])
  (get-by [device pred] [device pred options])
  (get-one [device pred] [device pred options])
  (add-data [device data-unit])
  (edit-data [device data-unit])
  (delete-data [device data-unit])
  (with-repository [device handler]))

(defprotocol PKeyValueDataRepository
  (key-exists? [device key])
  (get-keys [device] [device options])
  (get-keys-by [device re] [device re options])
  (delete-key [device key])
  (get-key-value [device key] [device key options])
  (set-key-value [device key value] [device key value options]))

(defn data-device
  [device-service]
  (let [[device-ns device-fn] (map symbol (-> device-service str (string/split #"/")))]
    (use [device-ns :only [device-fn]])
    (var-get (ns-resolve device-ns device-fn))))

(s/fdef data-device
        :args (s/cat :device-service symbol?)
        :ret var?)