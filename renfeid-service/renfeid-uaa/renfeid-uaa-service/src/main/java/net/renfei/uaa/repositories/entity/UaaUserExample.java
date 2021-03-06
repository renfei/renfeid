package net.renfei.uaa.repositories.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UaaUserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UaaUserExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUuidIsNull() {
            addCriterion("uuid is null");
            return (Criteria) this;
        }

        public Criteria andUuidIsNotNull() {
            addCriterion("uuid is not null");
            return (Criteria) this;
        }

        public Criteria andUuidEqualTo(String value) {
            addCriterion("uuid =", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotEqualTo(String value) {
            addCriterion("uuid <>", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThan(String value) {
            addCriterion("uuid >", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidGreaterThanOrEqualTo(String value) {
            addCriterion("uuid >=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThan(String value) {
            addCriterion("uuid <", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLessThanOrEqualTo(String value) {
            addCriterion("uuid <=", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidLike(String value) {
            addCriterion("uuid like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotLike(String value) {
            addCriterion("uuid not like", value, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidIn(List<String> values) {
            addCriterion("uuid in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotIn(List<String> values) {
            addCriterion("uuid not in", values, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidBetween(String value1, String value2) {
            addCriterion("uuid between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUuidNotBetween(String value1, String value2) {
            addCriterion("uuid not between", value1, value2, "uuid");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedIsNull() {
            addCriterion("email_verified is null");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedIsNotNull() {
            addCriterion("email_verified is not null");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedEqualTo(Boolean value) {
            addCriterion("email_verified =", value, "emailVerified");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedNotEqualTo(Boolean value) {
            addCriterion("email_verified <>", value, "emailVerified");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedGreaterThan(Boolean value) {
            addCriterion("email_verified >", value, "emailVerified");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("email_verified >=", value, "emailVerified");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedLessThan(Boolean value) {
            addCriterion("email_verified <", value, "emailVerified");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedLessThanOrEqualTo(Boolean value) {
            addCriterion("email_verified <=", value, "emailVerified");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedIn(List<Boolean> values) {
            addCriterion("email_verified in", values, "emailVerified");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedNotIn(List<Boolean> values) {
            addCriterion("email_verified not in", values, "emailVerified");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedBetween(Boolean value1, Boolean value2) {
            addCriterion("email_verified between", value1, value2, "emailVerified");
            return (Criteria) this;
        }

        public Criteria andEmailVerifiedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("email_verified not between", value1, value2, "emailVerified");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedIsNull() {
            addCriterion("phone_verified is null");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedIsNotNull() {
            addCriterion("phone_verified is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedEqualTo(Boolean value) {
            addCriterion("phone_verified =", value, "phoneVerified");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedNotEqualTo(Boolean value) {
            addCriterion("phone_verified <>", value, "phoneVerified");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedGreaterThan(Boolean value) {
            addCriterion("phone_verified >", value, "phoneVerified");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("phone_verified >=", value, "phoneVerified");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedLessThan(Boolean value) {
            addCriterion("phone_verified <", value, "phoneVerified");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedLessThanOrEqualTo(Boolean value) {
            addCriterion("phone_verified <=", value, "phoneVerified");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedIn(List<Boolean> values) {
            addCriterion("phone_verified in", values, "phoneVerified");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedNotIn(List<Boolean> values) {
            addCriterion("phone_verified not in", values, "phoneVerified");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedBetween(Boolean value1, Boolean value2) {
            addCriterion("phone_verified between", value1, value2, "phoneVerified");
            return (Criteria) this;
        }

        public Criteria andPhoneVerifiedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("phone_verified not between", value1, value2, "phoneVerified");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateIsNull() {
            addCriterion("registration_date is null");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateIsNotNull() {
            addCriterion("registration_date is not null");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateEqualTo(Date value) {
            addCriterion("registration_date =", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateNotEqualTo(Date value) {
            addCriterion("registration_date <>", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateGreaterThan(Date value) {
            addCriterion("registration_date >", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateGreaterThanOrEqualTo(Date value) {
            addCriterion("registration_date >=", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateLessThan(Date value) {
            addCriterion("registration_date <", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateLessThanOrEqualTo(Date value) {
            addCriterion("registration_date <=", value, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateIn(List<Date> values) {
            addCriterion("registration_date in", values, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateNotIn(List<Date> values) {
            addCriterion("registration_date not in", values, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateBetween(Date value1, Date value2) {
            addCriterion("registration_date between", value1, value2, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andRegistrationDateNotBetween(Date value1, Date value2) {
            addCriterion("registration_date not between", value1, value2, "registrationDate");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andTotpIsNull() {
            addCriterion("totp is null");
            return (Criteria) this;
        }

        public Criteria andTotpIsNotNull() {
            addCriterion("totp is not null");
            return (Criteria) this;
        }

        public Criteria andTotpEqualTo(String value) {
            addCriterion("totp =", value, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpNotEqualTo(String value) {
            addCriterion("totp <>", value, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpGreaterThan(String value) {
            addCriterion("totp >", value, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpGreaterThanOrEqualTo(String value) {
            addCriterion("totp >=", value, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpLessThan(String value) {
            addCriterion("totp <", value, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpLessThanOrEqualTo(String value) {
            addCriterion("totp <=", value, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpLike(String value) {
            addCriterion("totp like", value, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpNotLike(String value) {
            addCriterion("totp not like", value, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpIn(List<String> values) {
            addCriterion("totp in", values, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpNotIn(List<String> values) {
            addCriterion("totp not in", values, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpBetween(String value1, String value2) {
            addCriterion("totp between", value1, value2, "totp");
            return (Criteria) this;
        }

        public Criteria andTotpNotBetween(String value1, String value2) {
            addCriterion("totp not between", value1, value2, "totp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpIsNull() {
            addCriterion("registration_ip is null");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpIsNotNull() {
            addCriterion("registration_ip is not null");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpEqualTo(String value) {
            addCriterion("registration_ip =", value, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpNotEqualTo(String value) {
            addCriterion("registration_ip <>", value, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpGreaterThan(String value) {
            addCriterion("registration_ip >", value, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpGreaterThanOrEqualTo(String value) {
            addCriterion("registration_ip >=", value, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpLessThan(String value) {
            addCriterion("registration_ip <", value, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpLessThanOrEqualTo(String value) {
            addCriterion("registration_ip <=", value, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpLike(String value) {
            addCriterion("registration_ip like", value, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpNotLike(String value) {
            addCriterion("registration_ip not like", value, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpIn(List<String> values) {
            addCriterion("registration_ip in", values, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpNotIn(List<String> values) {
            addCriterion("registration_ip not in", values, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpBetween(String value1, String value2) {
            addCriterion("registration_ip between", value1, value2, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andRegistrationIpNotBetween(String value1, String value2) {
            addCriterion("registration_ip not between", value1, value2, "registrationIp");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesIsNull() {
            addCriterion("trial_error_times is null");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesIsNotNull() {
            addCriterion("trial_error_times is not null");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesEqualTo(Integer value) {
            addCriterion("trial_error_times =", value, "trialErrorTimes");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesNotEqualTo(Integer value) {
            addCriterion("trial_error_times <>", value, "trialErrorTimes");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesGreaterThan(Integer value) {
            addCriterion("trial_error_times >", value, "trialErrorTimes");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("trial_error_times >=", value, "trialErrorTimes");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesLessThan(Integer value) {
            addCriterion("trial_error_times <", value, "trialErrorTimes");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesLessThanOrEqualTo(Integer value) {
            addCriterion("trial_error_times <=", value, "trialErrorTimes");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesIn(List<Integer> values) {
            addCriterion("trial_error_times in", values, "trialErrorTimes");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesNotIn(List<Integer> values) {
            addCriterion("trial_error_times not in", values, "trialErrorTimes");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesBetween(Integer value1, Integer value2) {
            addCriterion("trial_error_times between", value1, value2, "trialErrorTimes");
            return (Criteria) this;
        }

        public Criteria andTrialErrorTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("trial_error_times not between", value1, value2, "trialErrorTimes");
            return (Criteria) this;
        }

        public Criteria andLockTimeIsNull() {
            addCriterion("lock_time is null");
            return (Criteria) this;
        }

        public Criteria andLockTimeIsNotNull() {
            addCriterion("lock_time is not null");
            return (Criteria) this;
        }

        public Criteria andLockTimeEqualTo(Date value) {
            addCriterion("lock_time =", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotEqualTo(Date value) {
            addCriterion("lock_time <>", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeGreaterThan(Date value) {
            addCriterion("lock_time >", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lock_time >=", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeLessThan(Date value) {
            addCriterion("lock_time <", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeLessThanOrEqualTo(Date value) {
            addCriterion("lock_time <=", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeIn(List<Date> values) {
            addCriterion("lock_time in", values, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotIn(List<Date> values) {
            addCriterion("lock_time not in", values, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeBetween(Date value1, Date value2) {
            addCriterion("lock_time between", value1, value2, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotBetween(Date value1, Date value2) {
            addCriterion("lock_time not between", value1, value2, "lockTime");
            return (Criteria) this;
        }

        public Criteria andSecretLevelIsNull() {
            addCriterion("secret_level is null");
            return (Criteria) this;
        }

        public Criteria andSecretLevelIsNotNull() {
            addCriterion("secret_level is not null");
            return (Criteria) this;
        }

        public Criteria andSecretLevelEqualTo(Integer value) {
            addCriterion("secret_level =", value, "secretLevel");
            return (Criteria) this;
        }

        public Criteria andSecretLevelNotEqualTo(Integer value) {
            addCriterion("secret_level <>", value, "secretLevel");
            return (Criteria) this;
        }

        public Criteria andSecretLevelGreaterThan(Integer value) {
            addCriterion("secret_level >", value, "secretLevel");
            return (Criteria) this;
        }

        public Criteria andSecretLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("secret_level >=", value, "secretLevel");
            return (Criteria) this;
        }

        public Criteria andSecretLevelLessThan(Integer value) {
            addCriterion("secret_level <", value, "secretLevel");
            return (Criteria) this;
        }

        public Criteria andSecretLevelLessThanOrEqualTo(Integer value) {
            addCriterion("secret_level <=", value, "secretLevel");
            return (Criteria) this;
        }

        public Criteria andSecretLevelIn(List<Integer> values) {
            addCriterion("secret_level in", values, "secretLevel");
            return (Criteria) this;
        }

        public Criteria andSecretLevelNotIn(List<Integer> values) {
            addCriterion("secret_level not in", values, "secretLevel");
            return (Criteria) this;
        }

        public Criteria andSecretLevelBetween(Integer value1, Integer value2) {
            addCriterion("secret_level between", value1, value2, "secretLevel");
            return (Criteria) this;
        }

        public Criteria andSecretLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("secret_level not between", value1, value2, "secretLevel");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserIsNull() {
            addCriterion("built_in_user is null");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserIsNotNull() {
            addCriterion("built_in_user is not null");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserEqualTo(Boolean value) {
            addCriterion("built_in_user =", value, "builtInUser");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserNotEqualTo(Boolean value) {
            addCriterion("built_in_user <>", value, "builtInUser");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserGreaterThan(Boolean value) {
            addCriterion("built_in_user >", value, "builtInUser");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserGreaterThanOrEqualTo(Boolean value) {
            addCriterion("built_in_user >=", value, "builtInUser");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserLessThan(Boolean value) {
            addCriterion("built_in_user <", value, "builtInUser");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserLessThanOrEqualTo(Boolean value) {
            addCriterion("built_in_user <=", value, "builtInUser");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserIn(List<Boolean> values) {
            addCriterion("built_in_user in", values, "builtInUser");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserNotIn(List<Boolean> values) {
            addCriterion("built_in_user not in", values, "builtInUser");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserBetween(Boolean value1, Boolean value2) {
            addCriterion("built_in_user between", value1, value2, "builtInUser");
            return (Criteria) this;
        }

        public Criteria andBuiltInUserNotBetween(Boolean value1, Boolean value2) {
            addCriterion("built_in_user not between", value1, value2, "builtInUser");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeIsNull() {
            addCriterion("password_expiration_time is null");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeIsNotNull() {
            addCriterion("password_expiration_time is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeEqualTo(Date value) {
            addCriterion("password_expiration_time =", value, "passwordExpirationTime");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeNotEqualTo(Date value) {
            addCriterion("password_expiration_time <>", value, "passwordExpirationTime");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeGreaterThan(Date value) {
            addCriterion("password_expiration_time >", value, "passwordExpirationTime");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("password_expiration_time >=", value, "passwordExpirationTime");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeLessThan(Date value) {
            addCriterion("password_expiration_time <", value, "passwordExpirationTime");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeLessThanOrEqualTo(Date value) {
            addCriterion("password_expiration_time <=", value, "passwordExpirationTime");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeIn(List<Date> values) {
            addCriterion("password_expiration_time in", values, "passwordExpirationTime");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeNotIn(List<Date> values) {
            addCriterion("password_expiration_time not in", values, "passwordExpirationTime");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeBetween(Date value1, Date value2) {
            addCriterion("password_expiration_time between", value1, value2, "passwordExpirationTime");
            return (Criteria) this;
        }

        public Criteria andPasswordExpirationTimeNotBetween(Date value1, Date value2) {
            addCriterion("password_expiration_time not between", value1, value2, "passwordExpirationTime");
            return (Criteria) this;
        }

        public Criteria andLockedIsNull() {
            addCriterion("locked is null");
            return (Criteria) this;
        }

        public Criteria andLockedIsNotNull() {
            addCriterion("locked is not null");
            return (Criteria) this;
        }

        public Criteria andLockedEqualTo(Boolean value) {
            addCriterion("locked =", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotEqualTo(Boolean value) {
            addCriterion("locked <>", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedGreaterThan(Boolean value) {
            addCriterion("locked >", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("locked >=", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedLessThan(Boolean value) {
            addCriterion("locked <", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedLessThanOrEqualTo(Boolean value) {
            addCriterion("locked <=", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedIn(List<Boolean> values) {
            addCriterion("locked in", values, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotIn(List<Boolean> values) {
            addCriterion("locked not in", values, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedBetween(Boolean value1, Boolean value2) {
            addCriterion("locked between", value1, value2, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("locked not between", value1, value2, "locked");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNull() {
            addCriterion("enabled is null");
            return (Criteria) this;
        }

        public Criteria andEnabledIsNotNull() {
            addCriterion("enabled is not null");
            return (Criteria) this;
        }

        public Criteria andEnabledEqualTo(Boolean value) {
            addCriterion("enabled =", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotEqualTo(Boolean value) {
            addCriterion("enabled <>", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThan(Boolean value) {
            addCriterion("enabled >", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledGreaterThanOrEqualTo(Boolean value) {
            addCriterion("enabled >=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThan(Boolean value) {
            addCriterion("enabled <", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledLessThanOrEqualTo(Boolean value) {
            addCriterion("enabled <=", value, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledIn(List<Boolean> values) {
            addCriterion("enabled in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotIn(List<Boolean> values) {
            addCriterion("enabled not in", values, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andEnabledNotBetween(Boolean value1, Boolean value2) {
            addCriterion("enabled not between", value1, value2, "enabled");
            return (Criteria) this;
        }

        public Criteria andLastNameIsNull() {
            addCriterion("last_name is null");
            return (Criteria) this;
        }

        public Criteria andLastNameIsNotNull() {
            addCriterion("last_name is not null");
            return (Criteria) this;
        }

        public Criteria andLastNameEqualTo(String value) {
            addCriterion("last_name =", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameNotEqualTo(String value) {
            addCriterion("last_name <>", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameGreaterThan(String value) {
            addCriterion("last_name >", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameGreaterThanOrEqualTo(String value) {
            addCriterion("last_name >=", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameLessThan(String value) {
            addCriterion("last_name <", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameLessThanOrEqualTo(String value) {
            addCriterion("last_name <=", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameLike(String value) {
            addCriterion("last_name like", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameNotLike(String value) {
            addCriterion("last_name not like", value, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameIn(List<String> values) {
            addCriterion("last_name in", values, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameNotIn(List<String> values) {
            addCriterion("last_name not in", values, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameBetween(String value1, String value2) {
            addCriterion("last_name between", value1, value2, "lastName");
            return (Criteria) this;
        }

        public Criteria andLastNameNotBetween(String value1, String value2) {
            addCriterion("last_name not between", value1, value2, "lastName");
            return (Criteria) this;
        }

        public Criteria andFirstNameIsNull() {
            addCriterion("first_name is null");
            return (Criteria) this;
        }

        public Criteria andFirstNameIsNotNull() {
            addCriterion("first_name is not null");
            return (Criteria) this;
        }

        public Criteria andFirstNameEqualTo(String value) {
            addCriterion("first_name =", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotEqualTo(String value) {
            addCriterion("first_name <>", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameGreaterThan(String value) {
            addCriterion("first_name >", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameGreaterThanOrEqualTo(String value) {
            addCriterion("first_name >=", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameLessThan(String value) {
            addCriterion("first_name <", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameLessThanOrEqualTo(String value) {
            addCriterion("first_name <=", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameLike(String value) {
            addCriterion("first_name like", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotLike(String value) {
            addCriterion("first_name not like", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameIn(List<String> values) {
            addCriterion("first_name in", values, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotIn(List<String> values) {
            addCriterion("first_name not in", values, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameBetween(String value1, String value2) {
            addCriterion("first_name between", value1, value2, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotBetween(String value1, String value2) {
            addCriterion("first_name not between", value1, value2, "firstName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

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