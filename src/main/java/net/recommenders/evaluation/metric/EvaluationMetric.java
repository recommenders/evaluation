package net.recommenders.evaluation.metric;

import java.util.Map;

/**
 *
 * @author Alejandro
 */
public interface EvaluationMetric<V> {

    public double getValue();

    public Map<V, Double> getValuePerUser();
}
