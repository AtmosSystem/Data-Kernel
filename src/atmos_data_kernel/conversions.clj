(ns atmos-data-kernel.conversions)

(defprotocol PIdentifierConversion
  (with-id [data])
  (to-entity-id [data]))

(defprotocol PFilterConversion
  (map-filters [data]))