(ns maps)

(import 'net.slreynolds.ds.ObjectSaver)
(import 'net.slreynolds.ds.model.BuilderOptions)
(import 'net.slreynolds.ds.export.ExporterOptions)
(import 'net.slreynolds.ds.export.GraphVizExporter)
(import 'net.slreynolds.ds.export.TulipExporter)
(import 'net.slreynolds.ds.Foo)
(import 'net.slreynolds.ds.Bar)

          
; Test a map collision
(defn mapcoll []
  (let [domain (map (fn [x] (Foo. x)) (range 1 6))
        codomain (map (fn [x] (Bar. x)) (range 1 6))
        x (apply hash-map (interleave domain codomain))
        y (assoc x  (Foo. 0x10001) (Bar.  0x10001))]
    (data/save-to-files (list x y) '("x" "assoc") "mapcoll")
    ))



(defn runall []
  (mapcoll)
  (print "thanks"))
  
