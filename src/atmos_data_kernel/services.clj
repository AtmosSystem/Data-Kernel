(ns atmos-data-kernel.services)

(defmulti all (fn [entity-type] (keyword entity-type)))
(defmulti filter (fn [entity-type by pred] (keyword entity-type)))
(defmulti get (fn [entity-type by property] (keyword entity-type)))
