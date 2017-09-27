(ns cognition.matcher
  (:require [clojure.test :refer :all]))

(defn header "returns the frame header" [frame] (first frame))

(defn body "returns the frame body" [frame] (rest frame))

(defn match
  "determine whether the pattern matches the constant given the current binding form and return updated binding form"
  [pattern constant binding]
  binding)
