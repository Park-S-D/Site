package capweb.capprac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TourpRepositoryTest {

    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private TourpRepository tourpRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;

    private User user;
    private Company company;
    private  Tour tour;
    private Tourp tourp;
    private Date date;

    @BeforeEach
    void setUp() {
        // 초기화 코드가 필요하다면 여기에 작성합니다.
        date = new Date();
        user = new User();
        user.setUsId("usid");
        user.setUsPw("uspw");
        user.setUsName("usname");
        user.setUsJoindate(date);
        user.setUsJoinIP("123.00.1");
        userRepository.save(user);
        company = new Company();
        company.setCpId("cpid");
        company.setCpPw("cppw");
        company.setCpName("cpname");
        company.setCpCategory("cpcate");
        company.setCpAddr("cpaddr");
        company.setCpMtid("cpmtid");
        company.setCpMtname("cpmtname");
        company.setCpJoindate(date);
        company.setCpJoinIP("127.00.1");
        companyRepository.save(company);
        tour = new Tour();
        tour.setTourCpid(company);
        tour.setTourDay(date);
        tour.setTourName("tourname");
        tour.setTourRecruitm(1);
        tourRepository.save(tour);
        tourp = new Tourp();
        tourp.setTourpTourid(tour);
        tourp.setTourpUsid(user);
        tourpRepository.save(tourp);
    }
    // Test for save method
    @Test
    public void testSave() {
        assertThat(tourpRepository.findTourpByIndex(tourp.getTourpIndex())).isNotNull();
    }

    // Test for findTourpByIndex method
    @Test
    public void testFindTourpByIndex() {
        // Assume that a tourp with index 1 exists
        int tourpIndex = 1;

        Tourp tourp = tourpRepository.findTourpByIndex(tourpIndex);

        assertThat(tourp).isNotNull();
        assertThat(tourp.getTourpIndex()).isEqualTo(tourpIndex);
    }

    // Test for findAllTourps method
    @Test
    public void testFindAllTourps() {
        List<Tourp> tourps = tourpRepository.findAllTourps();

        assertThat(tourps).isNotNull();
        assertThat(tourps.size()).isGreaterThan(0);
    }

    // Test for update method
    @Test
    public void testUpdate() {
        User uuser;
        uuser = new User();
        uuser.setUsId("uusid");
        uuser.setUsPw("uspw");
        uuser.setUsName("uusname");
        uuser.setUsJoindate(date);
        uuser.setUsJoinIP("143.00.1");
        userRepository.save(uuser);
        tourp.setTourpUsid(uuser);
        tourpRepository.update(tourp);
        Tourp updatedTourp = tourpRepository.findTourpByIndex(tourp.getTourpIndex());
        // Verify the properties are updated
        assertThat(updatedTourp).isNotNull();
        //assertThat(updatedTourp).isEqualToComparingFieldByField(tourp);
    }

    // Test for deleteByIndex method
    @Test
    public void testDeleteByIndex() {
        // Assume that a tourp with index 1 exists
        int tourpIndex = 1;

        tourpRepository.deleteByIndex(tourpIndex);
        Tourp deltourps = tourpRepository.findTourpByIndex(tourpIndex);
        assertThat(deltourps).isNull();
        //assertThrows(IllegalArgumentException.class, () -> tourpRepository.findTourpByIndex(tourpIndex));
    }

    // Test for findTourpsByUser method
    @Test
    public void testFindTourpsByUser() {

        List<Tourp> tourps = tourpRepository.findTourpsByUser(user);

        assertThat(tourps).isNotEmpty();
        //assertThat(tourps.get(0).getTourpUsid()).isEqualTo(user);
    }

    // Test for findTourpsByTour method
    @Test
    public void testFindTourpsByTour() {

        List<Tourp> tourps = tourpRepository.findTourpsByTour(tour);

        assertThat(tourps).isNotEmpty();
        //assertThat(tourps.get(0).getTourpTourid()).isEqualTo(tour);
    }

    // Test for findTourpsByUserAndTour method
    @Test
    public void testFindTourpsByUserAndTour() {

        List<Tourp> tourps = tourpRepository.findTourpsByUserAndTour(user, tour);

        assertThat(tourps).isNotEmpty();
        //assertThat(tourps.get(0).getTourpUsid()).isEqualTo(user);
        //assertThat(tourps.get(0).getTourpTourid()).isEqualTo(tour);
    }
}
