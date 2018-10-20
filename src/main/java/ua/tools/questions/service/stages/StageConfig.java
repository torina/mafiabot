package ua.tools.questions.service.stages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class StageConfig {



    @Bean
    @Scope(value = "prototype")
    public MafiaStage mafiaStage() {
        return new MafiaStage();
    }

    @Bean
    @Scope(value = "prototype")
    public PoliceStage policeStage() {
        return new PoliceStage();
    }

    @Bean
    @Scope(value = "prototype")
    public DoctorStage doctorStage() {
        return new DoctorStage();
    }


}
