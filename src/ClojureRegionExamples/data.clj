(ns data)

(import 'net.slreynolds.ds.ObjectSaver)
(import 'net.slreynolds.ds.model.BuilderOptions)
(import 'net.slreynolds.ds.export.ExporterOptions)
(import 'net.slreynolds.ds.export.GraphVizExporter)
(import 'net.slreynolds.ds.export.TulipExporter)
(import 'net.slreynolds.ds.Foo)
(import 'net.slreynolds.ds.Bar)


 
(defn save-to-files [objs names fileName]
      (let [gviz-exporter (GraphVizExporter.)
            gviz-saver (ObjectSaver. gviz-exporter)
            tulip-exporter (TulipExporter.)
            tulip-saver (ObjectSaver. tulip-exporter)
            opts {BuilderOptions/INLINE_STRINGS Boolean/FALSE}]
        (. gviz-saver save objs names (conj opts [ExporterOptions/OUTPUT_PATH (str "../graphs/clojure/" fileName  ".dot")]))
        (. tulip-saver save objs names (conj opts [ExporterOptions/OUTPUT_PATH (str "../graphs/clojure/" fileName  ".tlp")]))
            ))
            
(defn list-ex []
  (let [w (list (Foo. 1) (Foo. 2) (Foo. 3))
        x (rest w)
        y (conj  w (Foo. 0))]
        ;z (cons (Foo. 4) w)]
    (save-to-files (list w x y) '("w" "(rest w)" "(conj w (Foo. 0))" ) "list")
    ))


(defn amap-ex []
  (let [x {(Foo. 1) (Bar. 1) (Foo. 2) (Bar. 2)}
        y (assoc  x (Foo. 3) (Bar. 3))]
    (save-to-files (list x y) '("x" "after assoc ...") "amap")
    ))

(defn hmap-ex []
  (let [x (hash-map (Foo. 1) (Bar. 1) (Foo. 2) (Bar. 2))
        y (assoc  x (Foo. 3) (Bar. 3))]
    (save-to-files (list x y) '("x" "after assoc ...") "hmap")
    ))

(defn large-hmap []
  (let [domain (map (fn [x] (Foo. x)) (range 1 20))
        codomain (map (fn [x] (Bar. x)) (range 1 20))
        x (apply hash-map (interleave domain codomain))
        y (assoc x  21 (Bar. 77))]
    (save-to-files (list x y) '("x" "assoc") "large-hmap")
    ))

(defn vec-ex []
  (let [w (vector (Foo. 1) (Foo. 2) (Foo. 3))
        x (pop w)
        y (conj w (Foo. 4))
        z (assoc w 2 (Foo. 8))]
    (save-to-files (list w x y z) '("w" "pop" "conj" "assoc") "vec")
    ))

(defn large-vec []
  (let [x (vec (map (fn [x] (Foo. x)) (range 1 35)));(take 35 words))
        y (assoc x 34 (Foo. 88))]
    (save-to-files (list x y) '("x" "assoc") "large-vec")
    ))

(defn simple-range [i limit]
  (lazy-seq
            (when (< i limit)
              (cons i (simple-range (inc i) limit)))))

(defn lazylist []
   (let [w (simple-range 1 4)
         x (rest w)]
         ;y (range 1 4)]
     (save-to-files (list w x) '("original simple-range" "rest of ...") "lazylist")
     ))

(defn runall []
  (list-ex)
  (amap-ex)
  (hmap-ex)
  (large-hmap)
  (vec-ex)
  (large-vec)
  (lazylist)
  (print "thanks"))
  
