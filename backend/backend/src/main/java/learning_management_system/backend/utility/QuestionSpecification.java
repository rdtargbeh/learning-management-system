package learning_management_system.backend.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import learning_management_system.backend.entity.Question;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class QuestionSpecification {

    /**
     * Creates a specification to filter questions based on criteria.
     *
     * @param criteria The filtering criteria.
     * @return The specification for question filtering.
     */
    /**
     * Creates a specification to filter questions based on criteria.
     *
     * @param criteria The filtering criteria.
     * @return The specification for question filtering.
     */
    public static Specification<Question> matchesCriteria(QuestionFilterCriteria criteria) {
        return (Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getQuestionText() != null) {
                predicates.add(cb.like(root.get("questionText"), "%" + criteria.getQuestionText() + "%"));
            }
            if (criteria.getQuestionType() != null) {
                predicates.add(cb.equal(root.get("questionType"), criteria.getQuestionType()));
            }
            if (criteria.getDifficulty() != null) {
                predicates.add(cb.equal(root.get("difficulty"), criteria.getDifficulty()));
            }
            if (criteria.getAssessmentId() != null) {
                predicates.add(cb.equal(root.get("assessment").get("assessmentId"), criteria.getAssessmentId()));
            }
            if (criteria.getQuestionBankId() != null) {
                predicates.add(cb.equal(root.get("questionBank").get("questionBankId"), criteria.getQuestionBankId()));
            }
            if (criteria.getIsRandomized() != null) {
                predicates.add(cb.equal(root.get("isRandomized"), criteria.getIsRandomized()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Creates a specification for retrieving random questions with filtering.
     *
     * @param rulesJson The filtering rules in JSON format.
     * @return The specification for selecting random questions.
     */
    public static Specification<Question> randomQuestions(String rulesJson) {
        return (Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> rules;

            // ✅ Parse the JSON string into a Map
            try {
                rules = objectMapper.readValue(rulesJson, Map.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Failed to parse randomization rules.", e);
            }

            List<Predicate> predicates = new ArrayList<>();

            // ✅ Check if difficulty is present and apply filter
            if (rules.containsKey("difficulty") && rules.get("difficulty") != null) {
                predicates.add(cb.equal(root.get("difficulty"), rules.get("difficulty")));
            }

            // ✅ Check if tags exist and apply filter
            if (rules.containsKey("tags") && rules.get("tags") instanceof List) {
                predicates.add(root.get("tags").in((List<?>) rules.get("tags")));
            }

            query.orderBy(cb.asc(cb.function("RAND", Double.class))); // ✅ Randomize order

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

//    /**
//     * Creates a specification for retrieving random questions with filtering.
//     *
//     * @param rules The filtering rules in JSON format.
//     * @return The specification for selecting random questions.
//     */
//    public static Specification<Question> randomQuestions(String rules) {
//        return (Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            Random random = new Random();
//
//            // Apply filtering rules dynamically
//            if (rules.contains("difficulty")) {
//                predicates.add(cb.equal(root.get("difficulty"), rules.get("difficulty")));
//            }
//            if (rules.contains("tags")) {
//                predicates.add(root.get("tags").in(rules.get("tags")));
//            }
//
//            query.orderBy(cb.asc(cb.function("RAND", Double.class))); // Randomize order
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//    }

//    public static Specification<Question> matchesCriterias(QuestionFilterCriteria criteria) {
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (criteria.getQuestionText() != null) {
//                predicates.add(cb.like(root.get("questionText"), "%" + criteria.getQuestionText() + "%"));
//            }
//            if (criteria.getQuestionType() != null) {
//                predicates.add(cb.equal(root.get("questionType"), criteria.getQuestionType()));
//            }
//            if (criteria.getDifficulty() != null) {
//                predicates.add(cb.equal(root.get("difficulty"), criteria.getDifficulty()));
//            }
//            if (criteria.getAssessmentId() != null) {
//                predicates.add(cb.equal(root.get("assessment").get("assessmentId"), criteria.getAssessmentId()));
//            }
//            if (criteria.getQuestionBankId() != null) {
//                predicates.add(cb.equal(root.get("questionBank").get("questionBankId"), criteria.getQuestionBankId()));
//            }
//            if (criteria.getIsRandomized() != null) {
//                predicates.add(cb.equal(root.get("isRandomized"), criteria.getIsRandomized()));
//            }
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//    }

}
