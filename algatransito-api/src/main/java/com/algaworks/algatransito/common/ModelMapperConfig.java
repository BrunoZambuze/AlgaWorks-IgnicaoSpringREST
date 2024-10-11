package com.algaworks.algatransito.common;

import com.algaworks.algatransito.api.representationmodel.VeiculoRepresentationModel;
import com.algaworks.algatransito.domain.model.Veiculo;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //Declara que essa classe é um componente do Spring com o objetivo de configuar Beans
public class ModelMapperConfig {

    @Bean //Essa anotação informa ao Spring que esse método deve ser usado para criar e gerenciar a instância do ModelMapper
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        /*
        Nosso VeiculoRepresentationModel possui o atributo "numeroPlaca" que está sendo mapeado pelo Veiculo que possui o atributo "placa".
        Na hora de criar a instância de Model Mapper está gerando um conflito, pois não temos "numero" em nenhum dos atributos.
        O código abaixo está fazendo a configuração para resolver esse problema.

        -"modelMapper.createTypeMap(Veiculo.class, VeiculoRepresentationModel.class)": Criando um mapeamento de tipo, mudando de Veiculo para VeiculoRepresentationModel

        -".addMappings(mapper -> mapper.map(veiculo -> veiculo.getPlaca(),": Adicionamos um mapeamento específico. Aqui, o ModelMapper vai pegar o valor do método
          "getPlaca()" da classe Veiculo e utilizá-lo como origem dos dados.

        -"(VeiculoRepresentationModel veiculoRepresentationModel, String valor) -> veiculoRepresentationModel.setNumeroPlaca(valor)));": De segundo argumento é para onde
        será mapeado, nesse caso para a classe VeiculoRepresentationModel, e o segundo valor do parênteses é o valor que será mapeado. Depois pegamos a classe que queremos
        que receba o mapeamento e alteramos o campo que desejamos com o valor que pegamos da classe anterior
         */
        modelMapper.createTypeMap(Veiculo.class, VeiculoRepresentationModel.class)
                .addMappings(mapper -> mapper.map(veiculo -> veiculo.getPlaca(),
                        (VeiculoRepresentationModel veiculoRepresentationModel, String valor) -> veiculoRepresentationModel.setNumeroPlaca(valor)));

        return modelMapper;
    }

}
