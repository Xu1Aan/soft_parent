package com.soft.system.service;

import com.soft.common.utils.IdWorker;
import com.soft.domain.system.*;
import com.soft.domain.system.response.ExchangeTestResult;
import com.soft.system.dao.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @BelongsProject: soft_parent
 * @BelongsPackage: com.soft.system.service
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
            List<String> numbers = testAddressTaste.getNumbers();
            int i = 0;
            for (String address : addresses) {
                TestAddress testAddress = new TestAddress();
                testAddress.setAddress(address);
                testAddress.setTestId(testId);
                testAddress.setNumber(numbers.get(i++));
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
                    testUser.setAddressId(adds.get(i));
                    TestAddress testaddress = testAddressDao.findById(adds.get(i)).get();
                    int num = Integer.parseInt(testaddress.getNumber())-1;
                    testaddress.setRemain(num);
                    testAddressDao.save(testaddress);
                    i++;
                } else {
                    i = 0;
                    testUser.setAddressId(adds.get(i));
                    TestAddress testaddress = testAddressDao.findById(adds.get(i)).get();
                    int num = Integer.parseInt(testaddress.getNumber())-1;
                    testaddress.setRemain(num);
                    testAddressDao.save(testaddress);
                    i++;
                }
                testUserDao.save(testUser);

                User user = userDao.findById(id).get();
                if(user.getDoTaste() != null) {
                    user.setDoTaste(user.getDoTaste() + 1);
                } else {
                    user.setDoTaste(1);
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
                Predicate[] p = new Predicate[list.size()];
                criteriaQuery.where(criteriaBuilder.and(list.toArray(p)));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("date")));
                //return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
                return criteriaQuery.getRestriction();
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
           // List<String> numbers = testAddressTaste.getNumbers();
           // int i = 0;
            for (TestAddress testaddress: testAddresses) {
                if(testaddress.getId() == null){
                    String id = idWorker.nextId()+"";
                    testaddress.setId(id);
                    adds.add(id);
                } else {
                    adds.add(testaddress.getId());
                }
                //testaddress.setNumber(numbers.get(i++));
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
                user.setDoTaste(user.getDoTaste() + 1);
                testUserDao.deleteByTestId(test.getId());
            }
//            test.setRemain((Integer.parseInt(test.getRemain())+testUsers.size())+"");
            int num = testAddressTaste.getNumber();
            int teachersNum = testAddressTaste.getTeacher().size();
            System.out.println(num-teachersNum);
            test.setNumber(testAddressTaste.getNumber());
            test.setRemain((num-teachersNum)+"");
        }

        //更新任课教师
        if(testAddressTaste.getTeacher() != null) {
            List<String> teacher = testAddressTaste.getTeacher();
            int i = 0, j = 0;
            for (String id : teacher) {
                User user = userDao.findById(id).get();
                if (oldTestUser != null && j < oldTestUser.size()) {
                    TestUser oldtestUser = oldTestUser.get(j);
                    if (id.equals(oldtestUser.getUserId())) ;
                    oldTestUser.remove(oldtestUser);
                    user.setDoTaste(user.getDoTaste() + 1);
                }
                TestUser testUser = new TestUser();
                testUser.setId(idWorker.nextId() + "");
                testUser.setUserId(id);
                testUser.setTestId(test.getId());
                testUser.setIsTeacher(1);
                if (i < adds.size()) {
                    testUser.setAddressId(adds.get(i));
                    TestAddress testaddress = testAddressDao.findById(adds.get(i)).get();
                    int num = Integer.parseInt(testaddress.getNumber())-1;
                    testaddress.setRemain(num);
                    testAddressDao.save(testaddress);
                    i++;
                } else {
                    i = 0;
                    testUser.setAddressId(adds.get(i));
                    TestAddress testaddress = testAddressDao.findById(adds.get(i)).get();
                    int num = Integer.parseInt(testaddress.getNumber())-1;
                    testaddress.setRemain(num);
                    testAddressDao.save(testaddress);
                    i++;
                }
                testUserDao.save(testUser);


                if (user.getDoTaste() != null) {
                    user.setDoTaste(user.getDoTaste() + 1);
                } else {
                    user.setDoTaste(0);
                }
                userDao.save(user);
            }

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
            }
            int remain = Integer.parseInt(test.getRemain());
            if(remain > 0){
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
            user.setDoTaste(user.getDoTaste()+1);
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

    public List<TestAddress> findAddressForSelectById(String id) {
        List<TestAddress> addresses = testAddressDao.findByTestId(id);
        List<TestAddress> addressList = new ArrayList<>();
        for (TestAddress address: addresses ) {
            if(address.getRemain()>0)
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

        //更新考场剩余人数
        String addressId = testUser.getAddressId();
        TestAddress testAddress = testAddressDao.findById(addressId).get();
        testAddress.setRemain(testAddress.getRemain()-1);
        testAddressDao.save(testAddress);

        User user = userDao.findById(testUser.getUserId()).get();
        user.setDoTaste(user.getDoTaste()+1);
        userDao.save(user);
        return true;
    }

    @Transactional
    public boolean selectTestByAdmin(TestUser testUser) {
        List<TestUser> testUsers = testUserDao.findByTestIdAndUserId(testUser.getTestId(), testUser.getUserId());
        if (testUsers != null && testUsers.size()!=0) return false;
        testUser.setId(idWorker.nextId()+"");
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

        //更新考场剩余人数
        String addressId = testUser.getAddressId();
        TestAddress testAddress = testAddressDao.findById(addressId).get();
        testAddress.setRemain(testAddress.getRemain()-1);
        testAddressDao.save(testAddress);

        User user = userDao.findById(testUser.getUserId()).get();
        user.setDoTaste(user.getDoTaste()+1);
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

    public boolean testCancelById(String testId,String userId){
        if(testUserDao.existsByTestIdAndUserId(testId,userId)){
        List<TestUser> testUser = testUserDao.findByTestIdAndUserId(testId,userId);
        if(testUser.get(0).getIsTeacher()==1){
            System.out.println(testUser.get(0).getIsTeacher());
            return true;
        }
        else
            return false;
        }else
            return false;
    }

    public boolean testAdminCancelById(String testId,String userId){
        if(testUserDao.existsByTestIdAndUserId(testId,userId)){
            return false;
        }else
            System.out.println(testUserDao.existsByTestIdAndUserId(testId,userId));
            return true;
    }

    @Transactional
    public boolean cancelById(String testId,String userId) {
        User user = userDao.findById(userId).get();
        user.setDoTaste(user.getDoTaste()-1);
        userDao.save(user);

        Optional<Test> testOptional = testDao.findById(testId);
        if (testOptional!= null && testOptional.isPresent()) {
            Test test = testOptional.get();
            int remain = Integer.parseInt(test.getRemain()) + 1;
            if(remain > 0){
                test.setStatus(1);
            }
            test.setRemain(remain + "");
            testDao.save(test);
        }


        boolean flag = false;
        if(testUserDao.existsByTestIdAndUserId(testId,userId)) {
            List<TestUser> testUser = testUserDao.findByTestIdAndUserId(testId, userId);
            String addressId = testUser.get(0).getAddressId();
            Optional<TestAddress> address = testAddressDao.findById(addressId);
            TestAddress testAddress = address.get();
            testAddress.setRemain(testAddress.getRemain() + 1);
            testAddressDao.save(testAddress);
            int i  = testUserDao.deleteByTestIdAndUserId(testId, userId);
           if(i > 0){
               flag = true;
           }
        }
        return flag;
    }

    @Transactional
    public boolean cancelAdminById(String testId,String userId) {
        User user = userDao.findById(userId).get();
        user.setDoTaste(user.getDoTaste()-1);
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
            List<TestUser> testUser = testUserDao.findByTestIdAndUserId(testId, userId);
            String addressId = testUser.get(0).getAddressId();
            Optional<TestAddress> address = testAddressDao.findById(addressId);
            TestAddress testAddress = address.get();
            testAddress.setRemain(testAddress.getRemain() + 1);
            testAddressDao.save(testAddress);
            int i  = testUserDao.deleteByTestIdAndUserId(testId, userId);
            if(i > 0){
                flag = true;
            }
        }
        return flag;
    }

    public List<TestInfo> findTestInfoByid(String id) {
        List<Object> objects = testUserDao.slectTestInfo(id);
        List<TestInfo> testInfos = new ArrayList<>();
        for (Object object : objects) {
            Object[] rowArray = (Object[]) object;
            TestInfo testInfo = new TestInfo();
            testInfo.setUsername((String) rowArray[0]);
            testInfo.setAddress((String) rowArray[1]);
            testInfo.setMobile((String) rowArray[2]);
            testInfo.setUserId((String) rowArray[3]);
            testInfo.setTestId((String) rowArray[4]);
            testInfos.add(testInfo);
        }
        return testInfos;
    }

    public List<Test> findByCompanyId(String companyId) {
        List<Test> test = testDao.findByCompanyId(companyId);
        return test;
    }

    public TestAddress findAddressByTestId(String testId) {
        List<TestAddress> testAddresses = testAddressDao.findByTestId(testId);
        TestAddress goal = null;
        for (TestAddress testAddress : testAddresses) {
            if(testAddress.getRemain()>0){
                goal = testAddress;
            }
        }
        return goal;
    }


    public boolean exchangeTest(TestUser testUser) {
        boolean flag = testUserDao.existsByTestIdAndUserId(testUser.getTestId(), testUser.getExchangeTeacherId());
        if(!flag){
            testUserDao.save(testUser);
            return true;
        }else {
            return false;
        }
    }

    public TestUser findTestUserByTestIdAndUserId(String testId, String userId) {
        TestUser testUser = testUserDao.findByTestIdAndUserId(testId, userId).get(0);
        return  testUser;
    }

    public ExchangeTestResult findExchangeTest(String userId) {
        List<TestUser> testUsers = testUserDao.findByExchangeTeacherId(userId);
        List<ExchangeTest> exchangeTests = new ArrayList<>();
        if(testUsers != null) {
            for (TestUser testUser : testUsers) {
                ExchangeTest exchangeTest = new ExchangeTest();
                exchangeTest.setTestUserId(testUser.getId());
                String userId1 = testUser.getUserId();
                User user = userDao.findById(userId1).get();
                exchangeTest.setUsername(user.getUsername());
                String testId = testUser.getTestId();
                Test test = testDao.findById(testId).get();
                exchangeTest.setSubject(test.getSubject());
                exchangeTest.setDate(test.getDate());
                exchangeTest.setTime(test.getTime());
                exchangeTests.add(exchangeTest);
            }
            ExchangeTestResult exchangeTestResult = new ExchangeTestResult();
            exchangeTestResult.setExchangeTests(exchangeTests);
            return  exchangeTestResult;
        }else {
            return null;
        }
    }

    public boolean accessExchangeTest(String id, String userId) {
        TestUser testUser = testUserDao.findById(id).get();
        if(testUser.getExchangeTeacherId().equals(userId)){
            testUser.setUserId(userId);
            testUser.setExchangeTeacherId(null);
            testUserDao.save(testUser);
            return true;
        }else {
            return false;
        }
    }
}
