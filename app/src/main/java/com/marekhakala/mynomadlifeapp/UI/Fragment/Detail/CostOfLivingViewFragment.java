package com.marekhakala.mynomadlifeapp.UI.Fragment.Detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marekhakala.mynomadlifeapp.AppComponent;
import com.marekhakala.mynomadlifeapp.DataModel.CityCostOfLivingEntity;
import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Component.PriceValueView;
import com.marekhakala.mynomadlifeapp.UI.Fragment.AbstractBaseFragment;
import com.marekhakala.mynomadlifeapp.Utilities.ConstantValues;

import java.text.DecimalFormat;

import butterknife.Bind;

public class CostOfLivingViewFragment extends AbstractBaseFragment {

    public static final String FRAGMENT_TAG = "fragment_detail_view_cost_of_living";

    public static final String EXTRA_COST_OF_LIVING_ENTITY = "cost_of_living";
    public static final String EXTRA_SECOND_CURRENCY_RATE = "second_currency_rate";
    public static final String EXTRA_SECOND_CURRENCY_CODE = "second_currency_code";

    @Bind(R.id.city_item_detail_view_cost_of_living_nomad_cost_value)
    PriceValueView nomadCostValue;

    @Bind(R.id.city_item_detail_view_cost_of_living_expat_cost_of_living_value)
    PriceValueView expatCostOfLivingValue;

    @Bind(R.id.city_item_detail_view_cost_of_living_local_cost_of_living_value)
    PriceValueView localCostOfLivingValue;

    @Bind(R.id.city_item_detail_view_cost_of_living_hotel_room_value)
    PriceValueView hotelRoomValue;

    @Bind(R.id.city_item_detail_view_cost_of_living_airbnb_apartment_month_value)
    PriceValueView airbnbApartmentMonthValue;

    @Bind(R.id.city_item_detail_view_cost_of_living_airbnb_apartment_day_value)
    PriceValueView airbnbApartmentDayValue;

    @Bind(R.id.city_item_detail_view_cost_of_living_coworking_space_value)
    PriceValueView coworkingSpaceValue;

    @Bind(R.id.city_item_detail_view_cost_of_living_coca_cola_in_cafe_value)
    PriceValueView cocaColaInCafeValue;

    @Bind(R.id.city_item_detail_view_cost_of_living_pint_of_beer_in_bar_value)
    PriceValueView pintOfBeerInBarValue;

    @Bind(R.id.city_item_detail_view_cost_of_living_cappucino_in_cafe_value)
    PriceValueView cappucinoInCafeValue;

    @Bind(R.id.city_item_detail_view_cost_of_living_1_c1_in_c2_container)
    LinearLayout c1Inc2Container;

    @Bind(R.id.city_item_detail_view_cost_of_living_1_c1_in_c2_line_break)
    View c1Inc2LineBreak;

    @Bind(R.id.city_item_detail_view_cost_of_living_1_c1_in_c2_text)
    TextView c1Inc2Text;

    @Bind(R.id.city_item_detail_view_cost_of_living_1_c1_in_c2_value)
    PriceValueView c1Inc2Value;

    public static CostOfLivingViewFragment newInstance(CityCostOfLivingEntity entity,
                                                       String secondCurrencyCode, Double secondCurrencyRate) {
        CostOfLivingViewFragment fragment = new CostOfLivingViewFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_COST_OF_LIVING_ENTITY, entity);

        if(secondCurrencyCode != null && secondCurrencyRate != null) {
            bundle.putString(EXTRA_SECOND_CURRENCY_CODE, secondCurrencyCode);
            bundle.putDouble(EXTRA_SECOND_CURRENCY_RATE, secondCurrencyRate);
        }

        fragment.setArguments(bundle);

        return fragment;
    }

    public CostOfLivingViewFragment() {}

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        String secondCurrencyCode = (String)getArguments().get(EXTRA_SECOND_CURRENCY_CODE);
        Double secondCurrencyRate = (Double)getArguments().get(EXTRA_SECOND_CURRENCY_RATE);

        CityCostOfLivingEntity entity = (CityCostOfLivingEntity) getArguments().get(EXTRA_COST_OF_LIVING_ENTITY);

        if(entity != null) {
            if(entity.getNomadCost() != null)
                setupValues(nomadCostValue, entity.getNomadCost(), secondCurrencyCode, secondCurrencyRate);
            else
                setupValuesNone(nomadCostValue);

            if(entity.getExpatCostOfLiving() != null)
                setupValues(expatCostOfLivingValue, entity.getExpatCostOfLiving(), secondCurrencyCode, secondCurrencyRate);
            else
                setupValuesNone(expatCostOfLivingValue);

            if(entity.getLocalCostOfLiving() != null)
                setupValues(localCostOfLivingValue, entity.getLocalCostOfLiving(), secondCurrencyCode, secondCurrencyRate);
            else
                setupValuesNone(localCostOfLivingValue);

            if(entity.getHotelRoom() != null)
                setupValues(hotelRoomValue, entity.getHotelRoom(), secondCurrencyCode, secondCurrencyRate);
            else
                setupValuesNone(hotelRoomValue);

            if(entity.getAirbnbApartmentMonth() != null)
                setupValues(airbnbApartmentMonthValue, entity.getAirbnbApartmentMonth(), secondCurrencyCode, secondCurrencyRate);
            else
                setupValuesNone(airbnbApartmentMonthValue);

            if(entity.getAirbnbApartmentDay() != null)
                setupValues(airbnbApartmentDayValue, entity.getAirbnbApartmentDay(), secondCurrencyCode, secondCurrencyRate);
            else
                setupValuesNone(airbnbApartmentDayValue);

            if(entity.getCoworkingSpace() != null)
                setupValues(coworkingSpaceValue, entity.getCoworkingSpace(), secondCurrencyCode, secondCurrencyRate);
            else
                setupValuesNone(coworkingSpaceValue);

            if(entity.getCocaColaInCafe() != null)
                setupValues(cocaColaInCafeValue, entity.getCocaColaInCafe(), secondCurrencyCode, secondCurrencyRate);
            else
                setupValuesNone(cocaColaInCafeValue);

            if(entity.getPintOfBeerInBar() != null)
                setupValues(pintOfBeerInBarValue, entity.getPintOfBeerInBar(), secondCurrencyCode, secondCurrencyRate);
            else
                setupValuesNone(pintOfBeerInBarValue);

            if(entity.getCappucinoInCafe() != null)
                setupValues(cappucinoInCafeValue, entity.getCappucinoInCafe(), secondCurrencyCode, secondCurrencyRate);
            else
                setupValuesNone(cappucinoInCafeValue);

            if(secondCurrencyCode == null || secondCurrencyRate == null) {
                c1Inc2Container.setVisibility(View.GONE);
                c1Inc2LineBreak.setVisibility(View.GONE);
            } else {
                setupExchangeValues(c1Inc2Text, c1Inc2Value, secondCurrencyCode, secondCurrencyRate);
            }
        }
    }

    private void setupExchangeValues(TextView c1Inc2Text, PriceValueView c1Inc2Value,
                                     String secondCurrencyCode, Double secondCurrencyRate) {

        String labelValue = String.format(getResources().getString(R.string.currency_format_exchange),
                ConstantValues.USD_CURRENCY, secondCurrencyCode, secondCurrencyCode, ConstantValues.USD_CURRENCY);

        Double value1 = secondCurrencyRate;
        Double value2 = (1.0/secondCurrencyRate);

        c1Inc2Text.setText(labelValue);
        c1Inc2Value.setFirstCurrencyText(secondCurrencyCode);
        c1Inc2Value.setFirstValue(value1.toString());

        DecimalFormat formatter = new DecimalFormat(getContext().getString(R.string.currency_format_exchange_value_second));
        c1Inc2Value.setExchangeRate(true);
        c1Inc2Value.setSecondCurrencyText(ConstantValues.USD_CURRENCY);
        c1Inc2Value.setSecondValue(formatter.format(value2));
    }

    private void setupValues(PriceValueView view, Double value, String secondCurrency, Double secondCurrencyRate) {
        view.setFirstCurrencyText(ConstantValues.USD_CURRENCY);
        view.setFirstValue(value.toString());

        if(secondCurrency != null && secondCurrencyRate != null && !secondCurrency.isEmpty()) {
            view.setSecondCurrencyText(secondCurrency);
            Double valueInSecondCurrency = value*secondCurrencyRate;
            view.setSecondValue(valueInSecondCurrency.toString());
        } else {
            view.setSecondValue("");
            view.setSecondCurrencyText("");
            view.setSecondCurrencySymbol(null);
        }
    }

    private void setupValuesNone(PriceValueView view) {
        view.setFirstValue("");
        view.setFirstCurrencyText("");
        view.setFirstCurrencySymbol(null);

        view.setSecondValue("");
        view.setSecondCurrencyText("");
        view.setSecondCurrencySymbol(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_view_cost_of_living, container, false);
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_TAG;
    }

    @Override
    protected void setupComponent(AppComponent component) {}
}
