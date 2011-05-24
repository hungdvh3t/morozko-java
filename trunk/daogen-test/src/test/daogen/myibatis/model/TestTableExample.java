package test.daogen.myibatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TestTableExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public TestTableExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andTestIdIsNull() {
            addCriterion("test_id is null");
            return (Criteria) this;
        }

        public Criteria andTestIdIsNotNull() {
            addCriterion("test_id is not null");
            return (Criteria) this;
        }

        public Criteria andTestIdEqualTo(Long value) {
            addCriterion("test_id =", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdNotEqualTo(Long value) {
            addCriterion("test_id <>", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdGreaterThan(Long value) {
            addCriterion("test_id >", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdGreaterThanOrEqualTo(Long value) {
            addCriterion("test_id >=", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdLessThan(Long value) {
            addCriterion("test_id <", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdLessThanOrEqualTo(Long value) {
            addCriterion("test_id <=", value, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdIn(List<Long> values) {
            addCriterion("test_id in", values, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdNotIn(List<Long> values) {
            addCriterion("test_id not in", values, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdBetween(Long value1, Long value2) {
            addCriterion("test_id between", value1, value2, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIdNotBetween(Long value1, Long value2) {
            addCriterion("test_id not between", value1, value2, "testId");
            return (Criteria) this;
        }

        public Criteria andTestIntIsNull() {
            addCriterion("test_int is null");
            return (Criteria) this;
        }

        public Criteria andTestIntIsNotNull() {
            addCriterion("test_int is not null");
            return (Criteria) this;
        }

        public Criteria andTestIntEqualTo(Integer value) {
            addCriterion("test_int =", value, "testInt");
            return (Criteria) this;
        }

        public Criteria andTestIntNotEqualTo(Integer value) {
            addCriterion("test_int <>", value, "testInt");
            return (Criteria) this;
        }

        public Criteria andTestIntGreaterThan(Integer value) {
            addCriterion("test_int >", value, "testInt");
            return (Criteria) this;
        }

        public Criteria andTestIntGreaterThanOrEqualTo(Integer value) {
            addCriterion("test_int >=", value, "testInt");
            return (Criteria) this;
        }

        public Criteria andTestIntLessThan(Integer value) {
            addCriterion("test_int <", value, "testInt");
            return (Criteria) this;
        }

        public Criteria andTestIntLessThanOrEqualTo(Integer value) {
            addCriterion("test_int <=", value, "testInt");
            return (Criteria) this;
        }

        public Criteria andTestIntIn(List<Integer> values) {
            addCriterion("test_int in", values, "testInt");
            return (Criteria) this;
        }

        public Criteria andTestIntNotIn(List<Integer> values) {
            addCriterion("test_int not in", values, "testInt");
            return (Criteria) this;
        }

        public Criteria andTestIntBetween(Integer value1, Integer value2) {
            addCriterion("test_int between", value1, value2, "testInt");
            return (Criteria) this;
        }

        public Criteria andTestIntNotBetween(Integer value1, Integer value2) {
            addCriterion("test_int not between", value1, value2, "testInt");
            return (Criteria) this;
        }

        public Criteria andTestStringIsNull() {
            addCriterion("test_string is null");
            return (Criteria) this;
        }

        public Criteria andTestStringIsNotNull() {
            addCriterion("test_string is not null");
            return (Criteria) this;
        }

        public Criteria andTestStringEqualTo(String value) {
            addCriterion("test_string =", value, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringNotEqualTo(String value) {
            addCriterion("test_string <>", value, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringGreaterThan(String value) {
            addCriterion("test_string >", value, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringGreaterThanOrEqualTo(String value) {
            addCriterion("test_string >=", value, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringLessThan(String value) {
            addCriterion("test_string <", value, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringLessThanOrEqualTo(String value) {
            addCriterion("test_string <=", value, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringLike(String value) {
            addCriterion("test_string like", value, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringNotLike(String value) {
            addCriterion("test_string not like", value, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringIn(List<String> values) {
            addCriterion("test_string in", values, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringNotIn(List<String> values) {
            addCriterion("test_string not in", values, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringBetween(String value1, String value2) {
            addCriterion("test_string between", value1, value2, "testString");
            return (Criteria) this;
        }

        public Criteria andTestStringNotBetween(String value1, String value2) {
            addCriterion("test_string not between", value1, value2, "testString");
            return (Criteria) this;
        }

        public Criteria andTestDateIsNull() {
            addCriterion("test_date is null");
            return (Criteria) this;
        }

        public Criteria andTestDateIsNotNull() {
            addCriterion("test_date is not null");
            return (Criteria) this;
        }

        public Criteria andTestDateEqualTo(Date value) {
            addCriterionForJDBCDate("test_date =", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("test_date <>", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateGreaterThan(Date value) {
            addCriterionForJDBCDate("test_date >", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("test_date >=", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateLessThan(Date value) {
            addCriterionForJDBCDate("test_date <", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("test_date <=", value, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateIn(List<Date> values) {
            addCriterionForJDBCDate("test_date in", values, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("test_date not in", values, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("test_date between", value1, value2, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("test_date not between", value1, value2, "testDate");
            return (Criteria) this;
        }

        public Criteria andTestDecimalIsNull() {
            addCriterion("test_decimal is null");
            return (Criteria) this;
        }

        public Criteria andTestDecimalIsNotNull() {
            addCriterion("test_decimal is not null");
            return (Criteria) this;
        }

        public Criteria andTestDecimalEqualTo(Long value) {
            addCriterion("test_decimal =", value, "testDecimal");
            return (Criteria) this;
        }

        public Criteria andTestDecimalNotEqualTo(Long value) {
            addCriterion("test_decimal <>", value, "testDecimal");
            return (Criteria) this;
        }

        public Criteria andTestDecimalGreaterThan(Long value) {
            addCriterion("test_decimal >", value, "testDecimal");
            return (Criteria) this;
        }

        public Criteria andTestDecimalGreaterThanOrEqualTo(Long value) {
            addCriterion("test_decimal >=", value, "testDecimal");
            return (Criteria) this;
        }

        public Criteria andTestDecimalLessThan(Long value) {
            addCriterion("test_decimal <", value, "testDecimal");
            return (Criteria) this;
        }

        public Criteria andTestDecimalLessThanOrEqualTo(Long value) {
            addCriterion("test_decimal <=", value, "testDecimal");
            return (Criteria) this;
        }

        public Criteria andTestDecimalIn(List<Long> values) {
            addCriterion("test_decimal in", values, "testDecimal");
            return (Criteria) this;
        }

        public Criteria andTestDecimalNotIn(List<Long> values) {
            addCriterion("test_decimal not in", values, "testDecimal");
            return (Criteria) this;
        }

        public Criteria andTestDecimalBetween(Long value1, Long value2) {
            addCriterion("test_decimal between", value1, value2, "testDecimal");
            return (Criteria) this;
        }

        public Criteria andTestDecimalNotBetween(Long value1, Long value2) {
            addCriterion("test_decimal not between", value1, value2, "testDecimal");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table test_table
     *
     * @mbggenerated do_not_delete_during_merge Tue May 24 16:05:29 CEST 2011
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table test_table
     *
     * @mbggenerated Tue May 24 16:05:29 CEST 2011
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}