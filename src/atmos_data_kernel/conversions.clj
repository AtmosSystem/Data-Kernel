(ns atmos-data-kernel.conversions)

(defprotocol PDataIDConversion
  (data-with-id [data])
  (to-entity-id [data]))