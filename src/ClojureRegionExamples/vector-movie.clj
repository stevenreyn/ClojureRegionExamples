(ns vector-movie)


(import 'net.slreynolds.ds.Bar)

     
(def save-to-vmovie (data/create-save-to-files "../graphs/clojure.vector.movie"))

; Add next Bar to myVec (a Vector) and save
(defn add-to-end [myVec next]
  (let [nextVec (conj myVec (Bar. next))]
    (save-to-vmovie (list nextVec) '("vector") (str "vector" (+ next 1)))
    nextVec
    ))


(defn runall []
  (let [myVector [(Bar. 0)]]
    (save-to-vmovie (list myVector) '("vector") "vector1")
    (loop [i 1
           myVec myVector]
      (if (< i 60)
        (recur (+ i 1) (add-to-end myVec i)))))
  (print "thanks"))
  
