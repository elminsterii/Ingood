package com.fff.ingood.tools;

/*
 * Created by ElminsterII on 2018/7/9.
 */

import android.app.Activity;
import android.util.Pair;

import com.fff.ingood.global.GlobalProperty;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.CardRequirements;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.ShippingAddressRequirements;
import com.google.android.gms.wallet.TransactionInfo;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Contains helper static methods for dealing with the Payments API.
 * <p>
 * Many of the parameters used in the code are optional and are set here merely to call out their
 * existence. Please consult the documentation to learn more and feel free to remove ones not
 * relevant to your implementation.
 */
public class PaymentsUtil {
    private static final BigDecimal MICROS = new BigDecimal(1000000d);

    private PaymentsUtil() {
    }

    public static PaymentsClient createPaymentsClient(Activity activity) {
        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions.Builder()
                .setEnvironment(GlobalProperty.PAYMENTS_ENVIRONMENT)
                .build();
        return Wallet.getPaymentsClient(activity, walletOptions);
    }

    public static PaymentDataRequest createPaymentDataRequest(TransactionInfo transactionInfo) {
        PaymentMethodTokenizationParameters.Builder paramsBuilder =
                PaymentMethodTokenizationParameters.newBuilder()
                        .setPaymentMethodTokenizationType(
                                WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_PAYMENT_GATEWAY)
                        .addParameter("gateway", GlobalProperty.GATEWAY_TOKENIZATION_NAME);
        for (Pair<String, String> param : GlobalProperty.GATEWAY_TOKENIZATION_PARAMETERS) {
            paramsBuilder.addParameter(param.first, param.second);
        }

        return createPaymentDataRequest(transactionInfo, paramsBuilder.build());
    }

    public static PaymentDataRequest createPaymentDataRequestDirect(TransactionInfo transactionInfo) {
        PaymentMethodTokenizationParameters params =
                PaymentMethodTokenizationParameters.newBuilder()
                        .setPaymentMethodTokenizationType(
                                WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_DIRECT)

                        // Omitting the publicKey will result in a request for unencrypted data.
                        // Please refer to the documentation for more information on unencrypted
                        // requests.
                        .addParameter("publicKey", GlobalProperty.DIRECT_TOKENIZATION_PUBLIC_KEY)
                        .build();

        return createPaymentDataRequest(transactionInfo, params);
    }

    private static PaymentDataRequest createPaymentDataRequest(TransactionInfo transactionInfo, PaymentMethodTokenizationParameters params) {
        PaymentDataRequest request =
                PaymentDataRequest.newBuilder()
                        .setPhoneNumberRequired(false)
                        .setEmailRequired(true)
                        .setShippingAddressRequired(true)

                        // Omitting ShippingAddressRequirements all together means all countries are
                        // supported.
                        .setShippingAddressRequirements(
                                ShippingAddressRequirements.newBuilder()
                                        .addAllowedCountryCodes(GlobalProperty.SHIPPING_SUPPORTED_COUNTRIES)
                                        .build())

                        .setTransactionInfo(transactionInfo)
                        .addAllowedPaymentMethods(GlobalProperty.SUPPORTED_METHODS)
                        .setCardRequirements(
                                CardRequirements.newBuilder()
                                        .addAllowedCardNetworks(GlobalProperty.SUPPORTED_NETWORKS)
                                        .setAllowPrepaidCards(true)
                                        .setBillingAddressRequired(true)

                                        // Omitting this parameter will result in the API returning
                                        // only a "minimal" billing address (post code only).
                                        .setBillingAddressFormat(WalletConstants.BILLING_ADDRESS_FORMAT_FULL)
                                        .build())
                        .setPaymentMethodTokenizationParameters(params)

                        // If the UI is not required, a returning user will not be asked to select
                        // a card. Instead, the card they previously used will be returned
                        // automatically (if still available).
                        // Prior whitelisting is required to use this feature.
                        .setUiRequired(true)
                        .build();

        return request;
    }

    public static Task<Boolean> isReadyToPay(PaymentsClient client) {
        IsReadyToPayRequest.Builder request = IsReadyToPayRequest.newBuilder();
        for (Integer allowedMethod : GlobalProperty.SUPPORTED_METHODS) {
            request.addAllowedPaymentMethod(allowedMethod);
        }
        return client.isReadyToPay(request.build());
    }

    public static TransactionInfo createTransaction(String price) {
        return TransactionInfo.newBuilder()
                .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                .setTotalPrice(price)
                .setCurrencyCode(GlobalProperty.CURRENCY_CODE)
                .build();
    }

    public static String microsToString(long micros) {
        return new BigDecimal(micros).divide(MICROS).setScale(2, RoundingMode.HALF_EVEN).toString();
    }
}
