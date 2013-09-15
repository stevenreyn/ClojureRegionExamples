(ns map-movie)


(import 'net.slreynolds.ds.Bar)
(import 'net.slreynolds.ds.Foo)

     
(def save-to-mmovie (data/create-save-to-files "../graphs/clojure.map.movie"))

; Add next Bar to myMap (a Map) and save
(defn add-to-map [myMap fileID barID]
  (let [nextMap (assoc myMap (Bar. barID) (Foo. barID))]
    (save-to-mmovie (list nextMap) '("map") (str "map" fileID))
    nextMap
    ))

(defn compute-bar-id [i] 
  ; i can be 2 thru 6
  (case i
    2 32
    3 (* 32 2)
    4 (* 32 3)
    5 (* 32 4)
    6 (* 32 5)
    -1))

(defn runall []
  (let [myHashMap (hash-map (Bar. 0) (Foo. 0))]
    (save-to-mmovie (list myHashMap) '("map") "map1")
    (loop [i 2
           myMap myHashMap]
      (if (< i 6)
        (recur (+ i 1) (add-to-map myMap i (compute-bar-id i))))))
  (print "thanks"))
  
