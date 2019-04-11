package com.api.services;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.api.dao.UserDao;
import com.api.dto.Filter;
import com.api.exception.MarkException;
import com.api.utils.QueryBuilder;
import com.api.utils.RoleUtil;
import com.common.constants.Constants;
import com.common.models.User;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Rohit Raj
 *
 */

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private UserDao userDao;
	


	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
			Pattern.CASE_INSENSITIVE);
	private static Pattern DATE_PATTERN = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/(\\d\\d\\d\\d)");

	@Override
	public User save(User entity) {

		if (ObjectUtils.isEmpty(entity)) {
			log.error("Error while saving user.");
			throw new MarkException("Error while saving user.");
		}

		if (StringUtils.isEmpty(entity.getId())) {

			this.validateExistingUser(entity);

			entity.setCreatedDate();

		} else {

			entity = this.updateValidation(entity);
			entity.setUpdatedDate();
		}

		log.debug("Saved user.");
		return userDao.save(entity);
	}

	private User updateValidation(User entity) {
		return null;
	}

	private void validateExistingUser(User entity) {
		User user = this.getUserByUserName(entity.getUsername());
		if (!ObjectUtils.isEmpty(user)) {
			throw new MarkException("Username is already used.");
		}
	}

	@Override
	public List<User> saveAll(List<User> users) {

		return (List<User>) userDao.saveAll(users);
	}

	@Override
	public User update(Integer id, User entity) {
		return null;
	}

	@Override
	public User get(Integer id) {
		// Optional<User> user = userDao.findById(id);
		return userDao.findOneByIdAndStatus(id, Constants.STATUS_ACTIVE);
	}

	@Override
	public User getUserByIdAndCrn(String id, String crn) {
		// Optional<User> user = userDao.findById(id);
		return userDao.findOneByIdAndCrnAndStatus(id, crn, Constants.STATUS_ACTIVE);
	}

	@Override
	public User getUserByUserName(String userName) {
		return userDao.findOneByUsernameAndStatus(userName, Constants.STATUS_ACTIVE);
	}

	@Override
	public List<User> getAllUsers(Pageable pageable, List<Filter> filters) {
		try {

			Query query = QueryBuilder.createQuery(filters, pageable);

			if (ObjectUtils.isEmpty(filters)) {

				if (RoleUtil.isCurrentUserHasSuperAdminRole()) {
					query.addCriteria(Criteria.where(Constants.ROLE).is(Constants.ROLE_ADMIN));
				}
			}

			List<User> users = mongoTemplate.find(query, User.class);
			
			return users;
		} catch (Exception e) {
			log.error("Error while fetching users.", e);
			throw new MarkException("Error while fetching users.");
		}
	}

	

	@Override
	public List<User> uploadBulkUser(List<User> list) {
		if (ObjectUtils.isEmpty(list)) {
			throw new MarkException("User list cannot be empty or null");
		}

		LinkedList<User> errorlist = new LinkedList<>();
		LinkedList<User> userList = new LinkedList<>();
		LinkedList<String> errorMsg = new LinkedList<>();

		list.stream().forEach(user -> {

			if (StringUtils.isEmpty(user.getFullName()) && user.getFullName().length() > Constants.MAXSIZE) {
				errorMsg.add("User name length must be less than 50 characters");
			}

			if (StringUtils.isEmpty(user.getEmail())) {
				errorMsg.add("User email id cannot be empty");
			} else {
				if (!emailValidate(user.getEmail())) {
					errorMsg.add("Invalid email id");
				} else {
					User isExisting = userDao.findOneByUsernameAndStatus(user.getEmail(), Constants.STATUS_ACTIVE);
					if (!ObjectUtils.isEmpty(isExisting)) {
						errorMsg.add("User email id already exist");
					}
				}
			}

			if (ObjectUtils.isEmpty(user.getDob())) {
				errorMsg.add("User Date of birth cannot be empty");
			} else {
				if (!dateValidation(user.getDob())) {
					errorMsg.add("Invalid date of birth");
				}
			}

			if (ObjectUtils.isEmpty(user.getYearExperience())) {
				errorMsg.add("User experience cannot be empty");
			}

			if (ObjectUtils.isEmpty(user.getEmployeeId())) {
				errorMsg.add("User employee id cannot be empty");
			}
			if (ObjectUtils.isEmpty(user.getPhone())) {
				errorMsg.add("User mobile no cannot be empty");
			}

			if (!ObjectUtils.isEmpty(errorMsg)) {
				user.setError(errorMsg.toString());
				user.setErrorStatus(Constants.ERROR);
				errorlist.add(user);
			} else {
				userList.add(user);
			}

			errorMsg.clear();
		});

		userDao.saveAll(userList);
		return errorlist;
	}

	public static boolean emailValidate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	public static boolean dateValidation(String date) {
		return DATE_PATTERN.matcher(date).matches();
	}

	@Override
	public User getCompanyOwnerByCrn(String crn) {

		if (ObjectUtils.isEmpty(crn)) {
			log.error("crn cannot be null or empty.");
			throw new MarkException("crn cannot be null or empty.");
		}
		List<User> users = userDao.findAllByCrnAndAuthoritiesAndStatus(crn, Constants.ROLE_ADMIN, Constants.STATUS_ACTIVE); 
		
		return !ObjectUtils.isEmpty(users) && users.size() > 0 ? users.get(0) : null;
	}


}
