(ns maps)

(import 'net.slreynolds.ds.ObjectSaver)
(import 'net.slreynolds.ds.model.BuilderOptions)
(import 'net.slreynolds.ds.export.ExporterOptions)
(import 'net.slreynolds.ds.export.GraphVizExporter)
(import 'net.slreynolds.ds.export.TulipExporter)
(import 'net.slreynolds.ds.Foo)
(import 'net.slreynolds.ds.Bar)

     
(def save-to-maps (data/create-save-to-files "../graphs/clojure.test.maps"))

; Test a map collision
(defn mapcoll []
  (let [domain (map (fn [x] (Foo. x)) (range 1 6))
        codomain (map (fn [x] (Bar. x)) (range 1 6))
        x (apply hash-map (interleave domain codomain))
        y (assoc x  (Foo. 0x10001) (Bar.  0x10001))]
    (save-to-maps (list x y) '("x" "assoc") "mapcoll")
    ))


(defn runall []
  (mapcoll)
  (print "thanks"))
  
