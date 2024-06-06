package capweb.capprac.service;

//import CapstoneDesign.Backendserver.repository.UserRepository;
import capweb.capprac.entity.Company;
import capweb.capprac.entity.Plan;
import capweb.capprac.entity.User;
import capweb.capprac.repository.CompanyRepository;
import capweb.capprac.repository.PlanRepository;
import capweb.capprac.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;

    //일정아이디,일정명을 필수 유저와 회사를 선택받아 입력받게 하고 옵션값을 넣고 일정을 만들기-----!!!!!구현필요!!!!!
    @Transactional
    public Plan createPlan(Date planId, String planName, User user, Company company) {
        // 필수값 체크
        if (planId == null) {
            throw new IllegalArgumentException("Plan date cannot be null.");
        }
        if (planName == null || planName.trim().isEmpty()) {
            throw new IllegalArgumentException("Plan name cannot be null or empty.");
        }
        if (user == null && company == null) {
            throw new IllegalArgumentException("Either user or company must be provided.");
        }

        Plan plan = new Plan();
        plan.setPlanId(planId);
        plan.setPlanName(planName);
        if (user != null) {
            plan.setPlanUsid(user); // 유저 아이디가 비어있지 않을 경우
            plan.setPlanOpt(1);
        } else {
            plan.setPlanCpid(company); // 산업체 아이디가 비어있지 않을 경우
            plan.setPlanOpt(2);
        }
        planRepository.save(plan);
        return plan;
    }



    // 수정 - Plan의 planId와 planName 수정-----!!!!!구현 필요!!!!!
    public boolean updatePlan(int planIndex, Date newPlanId, String newPlanName) {
        Plan plan = planRepository.findPlanByIndex(planIndex);
        if (plan != null && newPlanId != null && (newPlanName != null&&!newPlanName.trim().isEmpty())) {
            plan.setPlanId(newPlanId);
            plan.setPlanName(newPlanName);
            planRepository.update(plan);
            return true; // 수정 성공
        }
        return false; // 해당 인덱스의 Plan이 없으면 수정 실패
    }

    // 삭제 - planIndex로 Plan 삭제-------!!!!!구현 필요!!!!!(특정 일정레코드를 선택해서 인덱스번호를 추출할수있다는 가정)
    public boolean deletePlan(int planIndex) {
        Plan plan = planRepository.findPlanByIndex(planIndex);
        if (plan != null) {
            planRepository.deleteByIndex(planIndex);
            return true; // 삭제 성공
        }
        return false; // 해당 인덱스의 Plan이 없으면 삭제 실패
    }
    // 조회 - planIndex로 Plan 찾기
    public Plan getPlanByIndex(int planIndex) {
        return planRepository.findPlanByIndex(planIndex);
    }

    // 조회 - 모든 Plan 찾기
    public List<Plan> getAllPlans() {
        return planRepository.findAllPlans();
    }

    // 조회 - planName으로 Plan 찾기
    public List<Plan> getPlansByName(String planName) {
        return planRepository.findPlansByName(planName);
    }

    // 조회 - planUsid로 Plan 찾기-----!!!조회에 필요!!!!
    public List<Plan> getPlansByUser(User planUsid) {
        return planRepository.findPlansByUser(planUsid);
    }

    // 조회 - planCpid로 Plan 찾기------!!!!조회에 필요!!!!!
    public List<Plan> getPlansByCompany(Company planCpid) {
        return planRepository.findPlansByCompany(planCpid);
    }

    // 조회 - planId(날짜)로 Plan 찾기
    public List<Plan> getPlansByDate(Date planId) {
        return planRepository.findPlansByDate(planId);
    }

    // 조회 - planOpt로 Plan 찾기
    public List<Plan> getPlansByOption(int planOpt) {
        return planRepository.findPlansByOption(planOpt);
    }

    // planId와 유저아이디로 Plan 찾기
    public List<Plan> getPlansByDateAndUser(Date planId, User planUsid) {
        return planRepository.findPlansByDateAndUser(planId, planUsid);
    }

    // planId와 산업체아이디로 Plan 찾기
    public List<Plan> getPlansByDateAndCompany(Date planId, Company planCpid) {
        return planRepository.findPlansByDateAndCompany(planId, planCpid);
    }
    //아이디와 월을 입력받아 아이디 체크하고  해당하는 일정 찾기----!!!조회할때 필요!!!
    public List<Plan> getPlansByUserIdAndMonth(String userId, int month) {
        List<User>existusers=userRepository.findUserById(userId);
        List<Company>existcompanies=companyRepository.findCompanyById(userId);
        if(existusers.isEmpty()&&existcompanies.isEmpty()){
            throw new IllegalArgumentException("User not found");
        }

        List<Plan>findplans=planRepository.findPlansByUserIdAndMonth(userId, month);
        if(findplans.isEmpty()){
            throw new IllegalArgumentException("plans not found");
        }
        else return findplans;
    }

    // 추가적인 서비스 메소드들...
}
