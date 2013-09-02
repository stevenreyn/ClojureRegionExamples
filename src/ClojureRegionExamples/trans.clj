(ns trans)


(import 'net.slreynolds.ds.Foo)
(import 'net.slreynolds.ds.Bar)

     
(def save-to-maps (data/create-save-to-files "../graphs/clojure.test.trans"))

; Do a transient
(defn trans []
  (let [w (vector (Foo. 1) (Foo. 2) (Foo. 3))]
    (save-to-maps (list w) '("w before") "before")
    (let [wt (transient w)
          _ (conj! wt (Foo. 4))
          wp (persistent! wt)]
      (save-to-maps (list w wt wp) '("w" "wt" "wp") "after")
    )))


(defn runall []
  (trans)
  (print "thanks"))
  
