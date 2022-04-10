package org.trb.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.trb.model.PrimaryAccount;
import org.trb.model.PrimaryTransaction;
import org.trb.repository.PrimaryAccountRepository;
import org.trb.repository.PrimaryTransactionRepository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Controller
public class MainController {

    Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private PrimaryTransactionRepository primaryTransactionRepository;

    @Autowired
    private PrimaryAccountRepository primaryAccountRepository;

    @GetMapping("/")
    public String root(Model model) {
        return "redirect:/userFront";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @GetMapping(path = "/paymentChart_PA/{paymentDate}/{primaryAccountNumber}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Object> getChartRepresentation(@PathVariable("paymentDate") String paymentDate,
                                                         @PathVariable(
                                                                 "primaryAccountNumber") String primaryAccountNumberStr, Model model){

        boolean isValid = true;
        String[] dates = paymentDate.split("=");
        String fromDateStr = dates[0];
        fromDateStr = fromDateStr.replaceAll("-", "/");
        fromDateStr = fromDateStr + " 00:00:00.0";
        String toDateStr = dates[1];
        toDateStr = toDateStr.replaceAll("-", "/");
        toDateStr = toDateStr + " 00:00:00.0";
        Date fromDate = null;
        Date toDate = null;
        String primaryAccountNumberString = primaryAccountNumberStr;
        Integer primaryAccountNumber = Integer.valueOf(primaryAccountNumberString);

        try {
            fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(fromDateStr);
            toDate = new SimpleDateFormat("dd/MM/yyyy").parse(toDateStr);
        } catch (ParseException e) {
            isValid = false;
            e.getMessage();
        }

        //Getting the default zone id
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Converting the date to Instant
        Instant fromInstant = fromDate.toInstant();
        Instant toInstant = toDate.toInstant();

        LocalDate localFromDate = fromInstant.atZone(defaultZoneId).toLocalDate();
        LocalDate localToDate = toInstant.atZone(defaultZoneId).toLocalDate();

        List<Map<Object, Object>> crossHairChartData = new ArrayList<>();
        Map<Object, Object> crossHairMapData = null;

        for (LocalDate date = localFromDate; date.isBefore(localToDate.plusDays(1)); date = date.plusDays(1)) {

            ZonedDateTime zonedDateTime = date.atStartOfDay(defaultZoneId);
            Date eachDay = Date.from(zonedDateTime.toInstant());

            LocalDate nextDay = date.plusDays(1);
            ZonedDateTime nextZonedDateTime = nextDay.atStartOfDay(defaultZoneId);
            Date nextEachDay = Date.from(nextZonedDateTime.toInstant());

            Optional<Collection<PrimaryTransaction>> eachDayPayments =
                    primaryTransactionRepository.findByDateBetween(eachDay, nextEachDay);

            PrimaryAccount primaryAccount = primaryAccountRepository.findByAccountNumber(primaryAccountNumber);
            BigDecimal eachDayPaymentAmount = new BigDecimal(0);

            List<String> addOns = new ArrayList<>();
            addOns.add("Deposit to Primary Account"); addOns.add("Between account transfer from Savings to Primary");

            List<String> cutOffs = new ArrayList<>();
            cutOffs.add("Withdraw from Primary Account"); cutOffs.add("Between account transfer from Primary to Savings");
            cutOffs.add("Transfer to");

            if(eachDayPayments.isPresent()){
                for(PrimaryTransaction eachPayment: eachDayPayments.get()){
                    if(addOns.contains(eachPayment.getDescription())) {
                        eachDayPaymentAmount = eachDayPaymentAmount.add(BigDecimal.valueOf(eachPayment.getAmount()));
                    }else if (cutOffs.contains(eachPayment.getDescription())){
                        eachDayPaymentAmount = eachDayPaymentAmount.subtract(BigDecimal.valueOf(eachPayment.getAmount()));
                    }else{
                        eachDayPaymentAmount = eachDayPaymentAmount;
                    }
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            String eachDayStr = simpleDateFormat.format(eachDay);

            crossHairMapData = new HashMap<>();

            crossHairMapData.put("x", eachDayStr);
            crossHairMapData.put("y", eachDayPaymentAmount);
            crossHairChartData.add(crossHairMapData);

        }

        model.addAttribute("dataSets", crossHairChartData);

        //Boolean response = termTestService.saveTermTest(termTestRequest);
        if (isValid) {
            return new ResponseEntity<>(crossHairChartData, HttpStatus.OK);
        } else {
            log.info("Invalid Date to Cross Hair Chart");
            return new ResponseEntity<>(crossHairChartData, HttpStatus.BAD_REQUEST);
        }

    }


}