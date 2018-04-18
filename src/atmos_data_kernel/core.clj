(ns atmos-data-kernel.core)

(defn update-map
  "Update a map with a new value"
  [map key value-fn]
  (update map key value-fn))
