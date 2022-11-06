package edu.baoss.userservice.service;

import edu.baoss.userservice.dto.AddressDto;
import edu.baoss.userservice.dto.UserDto;
import edu.baoss.userservice.feign.ResourceServiceFeignProxy;
import edu.baoss.userservice.model.Gender;
import edu.baoss.userservice.model.Role;
import edu.baoss.userservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static edu.baoss.userservice.Constants.DATE_AND_TIME_FORMAT;

@Service
@RequiredArgsConstructor
public class DefaultImportService implements ApplicationRunner {
    private final static Random RANDOM = new Random();
    private final static List<String> NAMES = List.of("Daniil", "Maxim", "Vladislav", "Nikita", "Artem", "Ivan", "Kirill", "Egor", "Ilya", "Andrey", "Alexei", "Bogdan", "Denis", "Dmitry, Yaroslav");
//            "Alina", "Daria", "Ekaterina", "Maria", "Natalia", "Sofia", "Julia", "Victoria", "Elizabeth", "Anna", "Veronica", "Uliana", "Alexandra", "Yana", "Christina");

    private final static List<String> SURNAMES = List.of("Smirnov", "Ivanov", "Kuznetsov", "Sokolov", "Popov", "Lebedev", "Kozlov", "Novikov", "Morozov", "Petrov", "Volkov", "Solovyov", "Vasiliev", "Zaitsev", "Pavlov", "Semyonov", "Golubev", "Vinogradov", "Bogdanov", "Vorobyov", "Fedorov", "Mikhailov", "Belyaev", "Tarasov", "Belov", "Komarov", "Orlov", "Kiselyov", "Makarov", "Andreev", "Kovalev", "Ilyin", "Gusev", "Titov", "Kuzmin", "Kudryavtsev", "Baranov", "Kulikov", "Alekseev", "Stepanov", "Yakovlev", "Sorokin", "Sergeev", "Romanov", "Zakharov", "Borisov", "Korolev", "Gerasimov", "Ponomarev", "Grigoriev");

    private final static List<String> STREETS = List.of( "Andrey Bubnov street", "Andrey Golovko street", "Andrey Ivanov street", "Andrew Malyshko street", "Botkina street", "Bratislava street", "Bratskaya street", "Brothers Zerov street", "Budyshchanskaya street", "Bukovinskaya street", "Bulgakov street", "Valkovskaya street", "Vandy Vasilevskaya street", "Vasily Blucher street", "Vasily Verkhovinets street", "Vasily Vyshyvanny street", "Vasily Donchuk street", "Vasily Zhukovsky street", "Dovzhenko street", "Dovnar-Zapolsky street", "Dokovskaya street", "Dokuchaevskaya street");
    @Value("${baoss.users.number-of-users-for-generation}")
    private int numberOfUsersForGeneration;
    @Value("${baoss.users.start-date}")
    private String startDateStr;
    @Value("${baoss.users.end-date}")
    private String endDateStr;

    private final UserService userService;
    

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (numberOfUsersForGeneration == 0 || userService.getAllUsers().size() > 10) {
            return;
        }
        Thread.sleep(7000);
        int maxUserId = userService.getAllUsers().stream()
                .mapToInt(userDto -> (int) userDto.getId())
                .max()
                .orElse(0);
        for (int i = maxUserId; i < numberOfUsersForGeneration; i++) {
            String userStr = "user" + i;
            UserDto userDto = UserDto.builder()
                    .id(i)
                    .name(NAMES.get(RANDOM.nextInt(NAMES.size())))
                    .surname(SURNAMES.get(RANDOM.nextInt(SURNAMES.size())))
                    .email(userStr + "@gmail.com")
                    .login(userStr)
                    .password("123")
                    .balance(500)
                    .contractNumber(10000+i)
                    .gender(Gender.MALE)
                    .regDate(DATE_AND_TIME_FORMAT.format(getRandomDate()))
                    .role(Role.USER)
                    .idCardNumber("000"+(10000+i))
                    .addresses(new HashSet<>())
                    .build();
            AddressDto addressDto = AddressDto.builder()
                    .country("Ukraine")
                    .city("Kyiv")
                    .street(STREETS.get(RANDOM.nextInt(STREETS.size())))
                    .buildingNum(""+(1 + RANDOM.nextInt(10)))
                    .roomNum(""+(1 + RANDOM.nextInt(50)))
                    .build();
            userDto.getAddresses().add(addressDto);
            userService.register(userDto);
        }
    }

    private Date getRandomDate() {
        String minDate = startDateStr;
        LocalDateTime endDate = LocalDateTime.parse(endDateStr);
        LocalDateTime generated;
        do {
            generated = LocalDateTime.parse(minDate);
            generated = generated.plusYears(RANDOM.nextInt(5));
            generated = generated.plusMonths(RANDOM.nextInt(12));
            generated = generated.plusDays(RANDOM.nextInt(31));
            generated = generated.plusHours(RANDOM.nextInt(10));
            generated = generated.plusMinutes(RANDOM.nextInt(60));
        } while (generated.isAfter(endDate));
        System.out.println(generated);
        return Date.from(generated.atZone(ZoneId.systemDefault()).toInstant());
    }
}
