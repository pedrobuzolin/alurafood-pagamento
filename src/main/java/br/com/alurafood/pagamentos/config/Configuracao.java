package br.com.alurafood.pagamentos.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuracao {
    @Bean
    public ModelMapper obeterModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.registerModule(new RecordModule());
        return mapper;
    }
}
