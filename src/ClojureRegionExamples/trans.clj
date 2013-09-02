(ns trans)


(import 'net.slreynolds.ds.Foo)
(import 'net.slreynolds.ds.Bar)

     
(def save-to-maps (data/create-save-to-files "../graphs/clojure.test.trans"))

; Do a transient
(defn trans []
  (let [w (vector (Foo. 1) (Foo. 2) (Foo. 3))]
    (save-to-maps (list w) '("w") "before")
    (let [wt (transient w)
          _ (save-to-maps (list w wt) '("w" "wt") "after1")
          _ (conj! wt (Foo. 4))
          _ (save-to-maps (list w wt) '("w" "wt") "after2")
          wp (persistent! wt)]
      (save-to-maps (list w wt wp) '("w" "wt" "wp") "after3")
    )))


(defn runall []
  (trans)
  (print "thanks"))
  
