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
  ; i can be 1 thru ...
  (* (- i 1) 32))
  
(defn runall []
  (let [myHashMap (hash-map (Bar. (compute-bar-id 1)) (Foo. (compute-bar-id 1)))]
    (save-to-mmovie (list myHashMap) '("map") "map1")
    (loop [i 2
           myMap myHashMap]
      (if (< i 33)
        (recur (+ i 1) (add-to-map myMap i (compute-bar-id i))))))
  (print "thanks"))
  
