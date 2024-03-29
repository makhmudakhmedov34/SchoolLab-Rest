package com.cydeo.service.impl;


import com.cydeo.client.FlagsApiClient;
import com.cydeo.client.WeatherApiClient;
import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.country_flag.ResponseCountry;
import com.cydeo.dto.weather_response.Weather;
import com.cydeo.entity.Address;
import com.cydeo.exception.NotFoundException;
import com.cydeo.util.MapperUtil;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Value("${access_key}")
    private String access_key;

    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;
    private final WeatherApiClient weatherApiClient;
    private final FlagsApiClient flagsApiClient;

    public AddressServiceImpl(AddressRepository addressRepository, MapperUtil mapperUtil, WeatherApiClient weatherApiClient, FlagsApiClient flagsApiClient) {
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
        this.weatherApiClient = weatherApiClient;
        this.flagsApiClient = flagsApiClient;
    }

    @Override
    public List<AddressDTO> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(address -> mapperUtil.convert(address, new AddressDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO findById(Long id) throws Exception {
        Address foundAddress = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Address Found!"));

        AddressDTO  addressDTO = mapperUtil.convert(foundAddress, new AddressDTO());
        addressDTO.setCurrentTemperature(retrieveTemperatureByCity(addressDTO.getCity()));
        addressDTO.setFlag(retrieveFlagByCountry(addressDTO.getCountry()));
        return addressDTO;
    }

    private String retrieveFlagByCountry(String country) {
        List<ResponseCountry> flags = flagsApiClient.getFlagByCountry(country);

        flags.stream().filter(i -> i != null || i.getFlags()!=null)
                .findFirst()
                .map(i -> i.getFlags().getPng())
                .orElseThrow(() -> new NotFoundException("Flags not found"));


        return flags.get(0).getFlags().getPng();
    }

    private Integer retrieveTemperatureByCity(String city) {

        Weather weather = weatherApiClient.getCurrentWeather(access_key,city);
        if(weather == null || weather.getCurrent() == null) {
            return null;
        }
        return weather.getCurrent().getTemperature();
    }

    @Override
    public AddressDTO update(AddressDTO addressDTO) throws Exception {

        addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new NotFoundException("No Address Found!"));

        Address addressToSave = mapperUtil.convert(addressDTO, new Address());

        addressRepository.save(addressToSave);

        return mapperUtil.convert(addressToSave, new AddressDTO());

    }

    @Override
    public AddressDTO create(AddressDTO addressDTO) throws Exception {

        Optional<Address> foundAddress = addressRepository.findById(addressDTO.getId());

        if (foundAddress.isPresent()) {
            throw new Exception("Address Already Exists!");
        }

        Address addressToSave = mapperUtil.convert(addressDTO, new Address());

        addressRepository.save(addressToSave);

        return mapperUtil.convert(addressToSave, new AddressDTO());

    }

}