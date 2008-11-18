package com.jbooktrader.indicator.velocity;

import com.jbooktrader.indicator.depth.*;
import com.jbooktrader.platform.indicator.*;
import com.jbooktrader.platform.marketbook.*;

/**
 * Velocity of balance
 */
public class BalanceVelocity extends Indicator {
    private final double fastMultiplier, slowMultiplier;
    private double fast, slow;
    private final DepthBalance depthBalance;

    public BalanceVelocity(DepthBalance depthBalance, int fastPeriod, int slowPeriod) {
        this.depthBalance = depthBalance;
        fastMultiplier = 2. / (fastPeriod + 1.);
        slowMultiplier = 2. / (slowPeriod + 1.);
    }

    @Override
    public void setMarketBook(MarketBook marketBook) {
        super.setMarketBook(marketBook);
        depthBalance.setMarketBook(marketBook);
    }

    @Override
    public void calculate() {
        depthBalance.calculate();
        double depthBalanceValue = depthBalance.getValue();
        fast += (depthBalanceValue - fast) * fastMultiplier;
        slow += (depthBalanceValue - slow) * slowMultiplier;
        value = fast - slow;
    }

    @Override
    public void reset() {
        depthBalance.calculate();
        fast = slow = depthBalance.getValue();
        value = 0;
    }
}