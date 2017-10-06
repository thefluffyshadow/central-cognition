;; Frame representation utilities.
;; $Revision$
;; =========================================================
(ns frames.utils)

(defn variable
  "Returns a variable with the given name"
  [x]
  (symbol (str "?" (name x))))

(defn is-var?
  "Determines if parameter is a variable"
  [x]
  (and (symbol? x)
       (re-matches #"^\?.*" (name x))))

(defn name-var
  "Returns the name of a variable"
  [x]
  {:pre [(is-var? x)]}
  (symbol (subs (name x) 1)))

(defn header
  "Returns the frame header."
  [frame]
  (first frame))

(defn body
  "Returns the frame body."
  [frame]
  (rest frame))

(defn roles
  "Returns the roles in the frame."
  [frame]
  (body frame))

(defn role-pair-name
  "Returns the name of a role pair."
  [role-pair]
  (first role-pair))

(defn role-pair-filler
  "Returns the filler of a role pair."
  [role-pair]
  (first (rest role-pair)))

(defn role-filler
  "Returns the filler of the specified role in the frame."
  [role frame]
  (let [pair (filter #(= role (first %)) (body frame))]
    (if (nil? pair)
        pair
        (role-pair-filler (first pair)))))

(defn map-to-alist
  "Converts a map into an association list"
  [m]
  {:pre [(map? m)]}
  (map #(list (symbol (name (key %))) (val %)) (seq m)))

(defn canonical-frame
  "Ensures that frame body is an association list;
  converts from map if necessary"
  [frm]
  (cond
    ((complement coll?) frm)
    frm
    
    (= '() frm)
    frm

    (and (map? (header frm))
         (map? (first (body frm))))
    (cons (canonical-frame (map-to-alist (header frm)))
          (canonical-frame (map-to-alist (body frm))))
    
    (map? (header frm))
    (cons (canonical-frame (map-to-alist (header frm)))
          (canonical-frame (body frm)))
    
    (map? (first (body frm)))
    (cons (canonical-frame (header frm))
          (canonical-frame (map-to-alist (first (body frm)))))
    
    :else
    (cons (canonical-frame (header frm)) (canonical-frame (body frm)))))


(def empty-binding-form '(true))

(declare match-var match-args match-canonical match)

(defn match-var
  "Parameters are a variable, a constant, and a binding form.
  If the variable has a binding, then the binding must match the constant;
  otherwise the binding form is updated to bind the variable to the constant."
  [variable constant binding-form]
  (let [var-value (role-filler (name-var variable) binding-form)]
    (if (nil? var-value)
      (concat binding-form (list (list (name-var variable) constant)))
      (match var-value constant binding-form))))

(defn match-args
  "Parameters are a list of role pairs ((role filler) (role filler) ...),
  a constant frame, and a binding form.
  Goes through the list of pairs and matches each pair against
  the corresponding role pair in the constant form,
  all of which must match."
  [role-args constant binding-form]
  (if (or (nil? role-args)
          (empty? role-args))
    binding-form
    (let [role-val (role-pair-filler (first role-args))
          const-val (role-filler (role-pair-name (first role-args)) constant)
          binding (match role-val const-val binding-form)]
      (if (or (nil? binding)
              (empty? binding))
        nil
        (match-args (rest role-args) constant binding)))))

(defn match
  "Parameters are three frames:
  a pattern which may contain variables,
  a constant which has no variables,
  a binding form which specifies any bindings that the variables
    in the pattern already have.
  Returns nil only if the match fails.
  A match that succeeds but which involved no variables returns
    the empty-binding-form."
  [pattern constant bindings]
  (let [binding-form (if (or (nil? bindings)
                             (empty? bindings))
                       empty-binding-form
                       bindings)]
    (cond
      ;; Unconstrained constant
      (or (nil? constant)
          (and (list? constant) (empty? constant)))
      binding-form

      ;; Exact match
      (= pattern constant)
      binding-form

      ;; Single variable pattern
      (is-var? pattern)
      (match-var pattern constant binding-form)

      ;; Non-collection constant or pattern
      (or (not (coll? constant))
          (not (coll? pattern)))
      nil

      ;; Same headers; check arguments
      (= (header pattern) (header constant))
      (match-args (roles pattern) constant binding-form)

      :else nil)))

(defn match-extended
  "Ensures that frames are in canonical form
  then invokes pattern matcher"
  [pattern constant bindings]
  (let [pat (canonical-frame pattern)
        con (canonical-frame constant)
        bin (canonical-frame bindings)]
    (match pat con bin)))


;; Sample frames
(def go-frame-pattern '(GO (ACTOR (PERSON (NAME Sam)))
                           (TO ?DESTINATION)
                           (FROM Home)))

(def go-frame-constant1 '(GO (ACTOR (PERSON (NAME Sam)))
                             (TO (STORE (TYPE Grocery)))
                             (FROM Home)))

(def go-frame-constant2 '(GO (ACTOR (PERSON))
                             (TO (STORE (TYPE Grocery)))
                             (FROM Home)))

(def go-frame-constant3 '(GO (ACTOR (PERSON (NAME Lee)))
                             (TO (STORE (TYPE Grocery)))
                             (FROM Home)))

(def go-frame-constant4 '(GO (ACTOR (PERSON (NAME Sam)))
                             (TIME Future)
                             (TO (STORE (TYPE Grocery)))
                             (FROM Home)))
