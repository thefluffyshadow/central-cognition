;; Frame representation library.
;; $Revision$
;;
;; =========================================================
(ns frames.core
  (:require [frames.utils :refer :all]))

(def went-frame '(GO (ACTOR (PERSON (NAME Sam))) (TO ?DESTINATION) (FROM (Home)) (TIME (Past))))
