(ns cognition.core
  (:gen-class))

(defn pair-name
  "Returns the name of the pair"
  [pair]
  (first pair))

(defn pair-filler
  "Returns the filler of the pair"
  [pair]
  (rest pair))

(defn match
  "determine whether the pattern matches the constant given the current binding form and return updated binding form"
  [pattern constant binding]
  binding)

(defn bullshit []
  (print "bullshit"))

(defn -main
  "Counts the number of groups in a list."
  [& args]
  (bullshit))
