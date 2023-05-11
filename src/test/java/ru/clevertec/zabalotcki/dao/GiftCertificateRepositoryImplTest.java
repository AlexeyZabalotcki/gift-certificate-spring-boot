//package ru.clevertec.zabalotcki.dao;
//
//import jakarta.persistence.NoResultException;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import ru.clevertec.zabalotcki.GiftCertificateSpringBootApplication;
//import ru.clevertec.zabalotcki.builder.GiftCertificateBuilder;
//import ru.clevertec.zabalotcki.builder.TestBuilder;
//import ru.clevertec.zabalotcki.config.AppConfig;
//import ru.clevertec.zabalotcki.config.ContainersEnvironment;
//import ru.clevertec.zabalotcki.model.GiftCertificate;
//import ru.clevertec.zabalotcki.model.Tag;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = GiftCertificateSpringBootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ContextConfiguration(classes = {AppConfig.class})
//class GiftCertificateRepositoryImplTest extends ContainersEnvironment {
//
//    @Autowired
//    private GiftCertificateRepository giftCertificateRepository;
//
//    @Autowired
//    private TagRepository tagRepository;
//
//    private static GiftCertificate certificate;
//
//    @BeforeEach
//    void setUp() {
//        TestBuilder<GiftCertificate> certificateTestBuilder = new GiftCertificateBuilder();
//        certificate = certificateTestBuilder.build();
//        giftCertificateRepository.deleteAll();
//        tagRepository.deleteAll();
//        giftCertificateRepository.save(certificate);
//    }
//
//    @AfterEach
//    void tearDown() {
//        giftCertificateRepository.deleteAll();
//        tagRepository.deleteAll();
//    }
//
//    static Stream<Arguments> pageParams() {
//        return Stream.of(
//                Arguments.of(0, 3)
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("pageParams")
//    void checkFindAllShouldReturnExpectedList(Integer pageNumber, Integer pageSize) {
//        List<GiftCertificate> certificates = giftCertificateRepository.findAll();
//        assertEquals(1, certificates.size());
//    }
//
//    @Test
//    void checkFindByIdShouldReturnExpectedItem() {
//        GiftCertificate certificateById = giftCertificateRepository.findById(certificate.getId()).get();
//        assertEquals(certificate.getId(), certificateById.getId());
//    }
//
//    @ParameterizedTest
//    @MethodSource("pageParams")
//    void checkSaveShouldAddNewItemToDb(Integer pageNumber, Integer pageSize) {
//        List<GiftCertificate> beforeSave = giftCertificateRepository.findAll();
//        int sizeBeforeSave = beforeSave.size();
//        giftCertificateRepository.save(certificate);
//        List<GiftCertificate> afterSave = giftCertificateRepository.findAll();
//        int sizeAfterSave = afterSave.size();
//        int expectedSize = sizeBeforeSave + 1;
//        assertEquals(expectedSize, sizeAfterSave);
//    }
//
////    @Test
////    void checkUpdateShouldUpdateItemFromDb() {
////        GiftCertificate byId = giftCertificateRepository.findById(certificate.getId()).get();
////        byId.setDescription("Description");
////        byId.setName("Name 1");
////        byId.setLastUpdateDate(LocalDateTime.now());
////        Long id = byId.getId();
////        String name = byId.getName();
////        String description = byId.getDescription();
////        int duration = byId.getDuration();
////        List<Tag> tags = byId.getTags();
////        BigDecimal price = byId.getPrice();
////        giftCertificateRepository.update(id, name, description, duration, price, tags);
////        GiftCertificate expected = giftCertificateRepository.findById(certificate.getId()).get();
////        assertNotEquals(expected, certificate);
////    }
//
//    @Test
//    void checkDeleteByIdShouldDeleteItemFromDb() {
//        giftCertificateRepository.deleteById(certificate.getId());
//        assertThrows(NoResultException.class, () -> giftCertificateRepository.findById(certificate.getId()));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"certificate", "cer", "is", "Tag"})
////    @MethodSource("pageParams")
//    void checkSearchByNameOrDescriptionOrTagsShouldDeleteItemFromDb(String value, Integer pageNumber, Integer pageSize) {
//        addNewGiftCertificate();
//        List<GiftCertificate> expectedCertificates = giftCertificateRepository.findByNameOrDescriptionOrTags(value);
//        List<GiftCertificate> allCertificates = giftCertificateRepository.findAll();
//        assertNotEquals(expectedCertificates, allCertificates);
//    }
//
//    private void addNewGiftCertificate() {
//        TestBuilder<GiftCertificate> testBuilder = new GiftCertificateBuilder();
//        GiftCertificate giftCertificate = testBuilder.build();
//        giftCertificate.setDescription("1233");
//        giftCertificate.setName("Something");
//        giftCertificate.getTags().get(0).setName("another");
//        giftCertificateRepository.save(giftCertificate);
//    }
//
//    @ParameterizedTest
//    @MethodSource("pageParams")
//    void checkDeleteAllShouldDeleteAllItemsFromDb(Integer pageNumber, Integer pageSize) {
//        giftCertificateRepository.deleteAll();
//        List<GiftCertificate> all = giftCertificateRepository.findAll();
//        int expected = 0;
//        int actual = all.size();
//        assertEquals(expected, actual);
//    }
//}