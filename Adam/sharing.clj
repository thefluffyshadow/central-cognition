(ns cognition.sharing
  (:gen-class))

; Programmer:         Zachary Champion
; Project:            central-cognition
; File:               sharing.clj
; Date Last Updated:  08 September 2017

(defn -main [& args]
   (repeatedly 1 (partial shuffle ["Hi", "Hello", "Howdy", "Greetings", "Hey", "G'day", "Good day",
                                   "How are you", "What's up", "How goes it", "How do you do", "Hi there"])))
(-main)
