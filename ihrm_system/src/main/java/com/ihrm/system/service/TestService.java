package com.ihrm.system.service;

import com.ihrm.common.utils.IdWorker;
import com.ihrm.domain.system.*;
import com.ihrm.system.dao.TestAddressDao;
import com.ihrm.system.dao.TestDao;
import com.ihrm.system.dao.TestUserDao;
import com.ihrm.system.dao.UserDao;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @BelongsProject: ihrm_parent
 * @BelongsPackage: com.ihrm.system.service
 * @Author: XuAn
 * @CreateTime: 2020-03-06 12:22
 * @Version: 1.0
 */
@Service
@Transactional
@Log4j
public class TestService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private TestDao testDao;

    @Autowired
    private TestAddressDao testAddressDao;

    @Autowired
    private TestUserDao testUserDao;

    @Autowired
    private UserDao userDao;


    @Transactional
    public void addTest(TestAddressTaste testAddressTaste) {

        String testId = idWorker.nextId()+"";
        Test test = new Test();
        BeanUtils.copyProperties(testAddressTaste,test);
        test.setId(testId);

        //时间转换
        Date createDate = new Date();
        Date date = testAddressTaste.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = simpleDateFormat.format(date);
        test.setDate(sDate);
        test.setTime(testAddressTaste.getTime());
        test.setNoticeTime(date);
        test.setCreateTime(createDate);
        test.setStatus(1);

        if(testAddressTaste.getTeacher() != null){
            test.setNumber(Integer.parseInt(test.getRemain()));
            test.setRemain((Integer.parseInt(test.getRemain())-testAddressTaste.getTeacher().size())+"");
            if((Integer.parseInt(test.getRemain())-testAddressTaste.getTeacher().size())<=0){
                test.setStatus(0);
            }
        }
        testDao.save(test);

        List<String> adds = new ArrayList<>();
        if (testAddressTaste.getAddress() != null) {
            List<String> addresses = testAddressTaste.getAddress();
            for (String address : addresses) {
                TestAddress testAddress = new TestAddress();
                testAddress.setAddress(address);
                testAddress.setTestId(testId);
                String ads = idWorker.nextId()+"";
                adds.add(ads);
                testAddress.setId(ads);
                testAddressDao.save(testAddress);
            }
        }

        if(testAddressTaste.getTeacher() != null){
            List<String> teacher = testAddressTaste.getTeacher();
            int i=0;
            for (String id :teacher){
                TestUser testUser = new TestUser();
                testUser.setId(idWorker.nextId()+"");
                testUser.setUserId(id);
                testUser.setTestId(testId);
                testUser.setIsTeacher(1);
                if (i < adds.size()) {
                    testUser.setAddressId(adds.get(i++));
                } else {
                    i = 0;
                    testUser.setAddressId(adds.get(i++));
                }
                testUserDao.save(testUser);

                User user = userDao.findById(id).get();
                if(user.getTaste() != null) {
                    user.setTaste(user.getTaste() - 1);
                } else {
                    user.setTaste(-1);
                }
                userDao.save(user);
            }
        }
    }

    public Page<Test> findAll(Map map, int page, int size) {
        Specification<Test> spec = new Specification<Test>(){

            private static final long serialVersionUID = -8384562969912769055L;

            public Predicate toPredicate(Root<Test> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder){
                List<Predicate> list = new ArrayList<>();

                //根据请求的companyId是否为空构造查询条件
                if(!StringUtils.isEmpty(map.get("companyId"))) {
                    list.add(criteriaBuilder.equal(root.get("companyId").as(String.class),(String)map.get("companyId")));
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Test> pageTest = testDao.findAll(spec, new PageRequest(page-1, size));
        return pageTest;
    }

    public List<Test> findMyTest(String id){
        List<TestUser> testUsers = testUserDao.findByUserId(id);
        List<Test> tests = new ArrayList<>();
        for (TestUser testUser: testUsers) {
            Test test = testDao.findById(testUser.getTestId()).get();
            tests.add(test);
        }
        return tests;
    }

    public Test findById(String id) {
        return testDao.findById(id).get();
    }


    @Transactional
    public void update(TestAddressTaste testAddressTaste) {
        Test test = new Test();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = simpleDateFormat.format(testAddressTaste.getDate());
        BeanUtils.copyProperties(testAddressTaste,test);
        test.setDate(sDate);

        List<String> adds = new ArrayList<>();
        testAddressDao.deleteByTestId(test.getId());
        if(testAddressTaste.getTestAddresses().size()!=0){
            List<TestAddress> testAddresses = testAddressTaste.getTestAddresses();
            for (TestAddress testaddress: testAddresses) {
                if(testaddress.getId() == null){
                    String id = idWorker.nextId()+"";
                    testaddress.setId(id);
                    adds.add(id);
                } else {
                    adds.add(testaddress.getId());
                }
                testaddress.setTestId(test.getId());
                testAddressDao.save(testaddress);
            }
        }
       //查询任课教师
        List<TestUser> testUsers = findByTestId(test.getId());
        //保存非任课教师
        List<TestUser> oldTestUser = new ArrayList<>();
        if(testUsers != null){
            for (TestUser testUser: testUsers ) {
                if(testUser.getIsTeacher() != 1){
                    oldTestUser.add(testUser);
                }
                User user = userDao.findById(testUser.getUserId()).get();
                user.setTaste(user.getTaste() + 1);
                testUserDao.deleteByTestId(test.getId());
            }
            test.setRemain((Integer.parseInt(test.getRemain())+testUsers.size())+"");
        }

        //更新任课教师
        if(testAddressTaste.getTeacher() != null) {
            List<String> teacher = testAddressTaste.getTeacher();
            int i = 0, j = 0;
            for (String id : teacher) {
                User user = userDao.findById(id).get();
                if (oldTestUser != null && j < oldTestUser.size()) {
                    TestUser oldtestUser = oldTestUser.get(j++);
                    if (id.equals(oldtestUser.getUserId())) ;
                    oldTestUser.remove(oldtestUser);
                    user.setTaste(user.getTaste() + 1);
                }
                TestUser testUser = new TestUser();
                testUser.setId(idWorker.nextId() + "");
                testUser.setUserId(id);
                testUser.setTestId(test.getId());
                testUser.setIsTeacher(1);
                if (i < adds.size()) {
                    testUser.setAddressId(adds.get(i++));
                } else {
                    i = 0;
                    testUser.setAddressId(adds.get(i++));
                }
                testUserDao.save(testUser);


                if (user.getTaste() != null) {
                    user.setTaste(user.getTaste() - 1);
                } else {
                    user.setTaste(-1);
                }
                userDao.save(user);
            }

            int number = test.getNumber() - testUsers.size();

            if (oldTestUser != null) {
                //更新已选教师
                for (TestUser testUser : oldTestUser) {
                    if (i < adds.size()) {
                        testUser.setAddressId(adds.get(i++));
                    } else {
                        i = 0;
                        testUser.setAddressId(adds.get(i++));
                    }
                    testUser.setId(test.getId());
                    testUser.setTestId(test.getId());
                    testUser.setIsTeacher(0);
                    testUserDao.save(testUser);
                }
                number = test.getNumber() - testUsers.size() - oldTestUser.size();
            }
            test.setRemain((number) + "");
            if(number > 0){
                test.setStatus(1);
            } else {
                test.setStatus(0);
            }
        }
        testDao.save(test);

    }

    @Transactional
    public void deleteById(String id) {
        List<TestUser> testUsers = testUserDao.findByTestId(id);
        for (TestUser testUser: testUsers) {
            User user = userDao.findById(testUser.getUserId()).get();
            user.setTaste(user.getTaste()+1);
            userDao.save(user);
        }
        if(testDao.existsById(id)) {
            testDao.deleteById(id);
        }
        testAddressDao.deleteByTestId(id);
        testUserDao.deleteByTestId(id);
    }

    public List<TestAddress> findAddressById(String id) {
        List<TestAddress> addresses = testAddressDao.findByTestId(id);
        List<TestAddress> addressList = new ArrayList<>();
        for (TestAddress address: addresses ) {
            addressList.add(address);
        }
        return  addressList;
    }


    @Transactional
    public boolean selectTest(TestUser testUser) {
        List<TestUser> testUsers = testUserDao.findByTestIdAndUserId(testUser.getTestId(), testUser.getUserId());
        if (testUsers != null && testUsers.size()!=0) return false;
        testUser.setId(idWorker.nextId()+"");
        testUser.setIsTeacher(0);
        testUserDao.save(testUser);

        Test test = testDao.findById(testUser.getTestId()).get();
        if(test.getRemain() != null ) {
            Integer testRemian = Integer.parseInt(test.getRemain()) - 1;
            if ( testRemian > 0 ) {
                test.setRemain(testRemian+ "");
            }  else {
                test.setRemain("0");
                test.setStatus(0);
            }
        }
        testDao.save(test);

        User user = userDao.findById(testUser.getUserId()).get();
        user.setTaste(user.getTaste()-1);
        userDao.save(user);
        return true;
    }

    public List<TestUser> findByTestId(String id) {
        Specification<TestUser> countCondition = new Specification<TestUser>() {
            private static final long serialVersionUID = -5635947285103522326L;

            @Override
            public Predicate toPredicate(Root<TestUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if(id != null){
                    predicateList.add(criteriaBuilder.equal(root.get("testId"), id));
                }
                predicateList.add(criteriaBuilder.equal(root.get("isTeacher"), 1));
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
        List<TestUser> testUsers = testUserDao.findAll(countCondition);
        return testUsers;
    }

    public String findAddress(String testId,String userId) {

        List<TestUser> testUsers = testUserDao.findByTestIdAndUserId(testId, userId);
        if(testUsers != null && testUsers.size() != 0){
            TestUser testUser = testUsers.get(0);
            String addressId = testUser.getAddressId();
            if(addressId != null) {
                TestAddress address = testAddressDao.findById(addressId).get();
                return address.getAddress();
            }
            return "无考试地址（或见通知)";
        } else {
            return "无考试地址（或见通知)";
        }

    }

    @Transactional
    public boolean cancelById(String testId,String userId) {
        User user = userDao.findById(userId).get();
        user.setTaste(user.getTaste()+1);
        userDao.save(user);

        Optional<Test> testOptional = testDao.findById(testId);
        if (testOptional!= null && testOptional.isPresent()) {
            Test test = testOptional.get();
            int remain = Integer.parseInt(test.getRemain()) + 1;
            test.setRemain(remain + "");
            testDao.save(test);
        }


        boolean flag = false;
        if(testUserDao.existsByTestIdAndUserId(testId,userId)) {
           int i  = testUserDao.deleteByTestIdAndUserId(testId, userId);
           if(i > 0){
               flag = true;
           }
        }
        return flag;
    }
}
