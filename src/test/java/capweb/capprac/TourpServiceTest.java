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
@Transactional
public class TourpServiceTest {

    @Autowired
    private TourpService tourpService;

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

    // Test for createTourp method
    @Test
    public void testCreateTourp() {
        User uuser;
        uuser = new User();
        uuser.setUsId("uusid");
        uuser.setUsPw("uspw");
        uuser.setUsName("uusname");
        uuser.setUsJoindate(date);
        uuser.setUsJoinIP("223.00.1");
        userRepository.save(uuser);
        //Tourp tourp = tourpService.createTourp(user, tour);
        Tourp tourp = tourpService.createTourp(uuser, tour);
        assertThat(tourp).isNotNull();
        assertThat(tourp.getTourpUsid()).isEqualTo(uuser);
        assertThat(tourp.getTourpTourid()).isEqualTo(tour);
    }

    // Test for deleteTourp method
    @Test
    public void testDeleteTourp() {
        // Assume that a tourp with index 1 exists
        int tourpIndex = 1;

        boolean result = tourpService.deleteTourp(tourpIndex);

        assertThat(result).isTrue();
        //assertThrows(IllegalArgumentException.class, () -> tourpService.getTourpByIndex(tourpIndex));
    }

    // Test for getAllTourps method
    @Test
    public void testGetAllTourps() {
        List<Tourp> tourps = tourpService.getAllTourps();

        assertThat(tourps).isNotNull();
        assertThat(tourps.size()).isGreaterThan(0);
    }

    // Test for getTourpByIndex method
    @Test
    public void testGetTourpByIndex() {
        // Assume that a tourp with index 1 exists
        int tourpIndex = 1;

        Tourp tourp = tourpService.getTourpByIndex(tourpIndex);

        assertThat(tourp).isNotNull();
        assertThat(tourp.getTourpIndex()).isEqualTo(tourpIndex);
    }

    // Test for getTourpsByUser method
    @Test
    public void testGetTourpsByUser() {
        List<Tourp> tourps = tourpService.getTourpsByUser(user);

        assertThat(tourps).isNotEmpty();
        assertThat(tourps.get(0).getTourpUsid()).isEqualTo(user);
    }

    // Test for getTourpsByTour method
    @Test
    public void testGetTourpsByTour() {
        List<Tourp> tourps = tourpService.getTourpsByTour(tour);

        assertThat(tourps).isNotEmpty();
        assertThat(tourps.get(0).getTourpTourid()).isEqualTo(tour);
    }

    // Test for getTourpsByUserAndTour method
    @Test
    public void testGetTourpsByUserAndTour() {
        List<Tourp> tourps = tourpService.getTourpsByUserAndTour(user, tour);

        assertThat(tourps).isNotEmpty();
        assertThat(tourps.get(0).getTourpUsid()).isEqualTo(user);
        assertThat(tourps.get(0).getTourpTourid()).isEqualTo(tour);
    }
}
