(ns data)

(import 'net.slreynolds.ds.ObjectSaver)
(import 'net.slreynolds.ds.model.BuilderOptions)
(import 'net.slreynolds.ds.export.ExporterOptions)
(import 'net.slreynolds.ds.export.GraphVizExporter)
(import 'net.slreynolds.ds.export.SimpleGraphVizExporter)
(import 'net.slreynolds.ds.Foo)
(import 'net.slreynolds.ds.Bar)


(defn create-save-to-files [dir]
   (fn  [objs names fileName]
      (let [gviz-exporter (GraphVizExporter.)
            gviz-saver (ObjectSaver. gviz-exporter)
            simple-gviz-exporter (SimpleGraphVizExporter.)
            simple-gviz-saver (ObjectSaver. simple-gviz-exporter)
            opts {BuilderOptions/INLINE_STRINGS Boolean/FALSE}]
        (. gviz-saver save objs names (conj opts [ExporterOptions/OUTPUT_PATH (str dir "/" fileName  ".dot")]))
        (. simple-gviz-saver save objs names (conj opts [ExporterOptions/OUTPUT_PATH (str dir "/" fileName  "_simple.dot")]))
            )))
           
(def save-to-clojure (create-save-to-files "../graphs/clojure"))

(defn list-ex []
  (let [w (list (Foo. 1) (Foo. 2))
        x (rest w)
        y (conj  w (Foo. 0))]
    (save-to-clojure (list w) '("w") "list")
    (save-to-clojure (list w x y) '("w" "(rest w)" "(conj w (Foo. 0))" ) "list-ops")
    ))

(defn list-cons []
  (let [w (list (Foo. 1) (Foo. 2))
        z (cons (Foo. 4) w)]
    (save-to-clojure (list w z) '("w"  "(cons w (Foo. 4))" ) "list-cons")
    ))

(defn amap-ex []
  (let [x {(Foo. 1) (Bar. 1) (Foo. 2) (Bar. 2)}
        y (assoc  x (Foo. 3) (Bar. 3))]
    (save-to-clojure (list x) '("x") "small-map")
    (save-to-clojure (list x y) '("x" "after assoc ...") "small-map-ops")
    ))

(defn hmap-ex []
  (let [x (hash-map (Foo. 1) (Bar. 1) (Foo. 2) (Bar. 2))
        y (assoc  x (Foo. 3) (Bar. 3))]
    (save-to-clojure (list x) '("x") "hash-map")
    (save-to-clojure (list x y) '("x" "after assoc ...") "hash-map-ops")
    ))

(defn large-hmap []
  (let [domain (map (fn [x] (Foo. x)) (range 1 20))
        codomain (map (fn [x] (Bar. x)) (range 1 20))
        x (apply hash-map (interleave domain codomain))
        y (assoc x  (Foo. 21) (Bar. 77))]
    (save-to-clojure (list x y) '("x" "assoc") "large-hmap")
    ))

(defn vec-ex []
  (let [w (vector (Foo. 1) (Foo. 2) (Foo. 3))
        x (pop w)
        y (conj w (Foo. 4))
        z (assoc w 2 (Foo. 8))]
    (save-to-clojure (list w) '("w") "vec")
    (save-to-clojure (list w x) '("w" "pop") "vec-ops1")
    (save-to-clojure (list w x y) '("w" "pop" "conj") "vec-ops2")
    (save-to-clojure (list w x y z) '("w" "pop" "conj" "assoc") "vec-ops3")
    ))


(defn large-vec []
  (let [x (vec (map (fn [x] (Foo. x)) (range 1 35)));(take 35 words))
        y (assoc x 34 (Foo. 88))]
    (save-to-clojure (list x y) '("x" "assoc") "large-vec")
    ))

(defn simple-range [i limit]
  (lazy-seq
            (when (< i limit)
              (cons i (simple-range (inc i) limit)))))

(defn lazylist []
   (let [w (simple-range 1 4)
         x (rest w)]
         ;y (range 1 4)]
     (save-to-clojure (list w x) '("original simple-range" "rest of ...") "lazylist")
     ))

(defn runall []
  (list-ex)
  (list-cons)
  (amap-ex)
  (hmap-ex)
  (large-hmap)
  (vec-ex)
  (large-vec)
  (lazylist)
  (print "thanks"))
  
