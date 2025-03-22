/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.laba1;

/**
 *
 * @author ivis2
 */
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;

public class Calculator {
    public double[][] calculateAll(double[][] data) {
        int numRows = data.length;
        int numCols = data[0].length;
        double[][] results = new double[numCols][11];

        for (int j = 0; j < numCols; j++) {
            double[] columnData = getColumn(data, j);
            int n = columnData.length;
            double mean = StatUtils.mean(columnData);
            double variance = StatUtils.variance(columnData);
            double stdDev = Math.sqrt(variance);
            double min = StatUtils.min(columnData);
            double max = StatUtils.max(columnData);
            double range = max - min;
            double geoMean = StatUtils.geometricMean(columnData);
            double coefVar = (stdDev / mean)*100;
            double[] confInterval = getConfidenceInterval(mean, stdDev, n);
            
            results[j][0] = geoMean;
            results[j][1] = mean;
            results[j][2] = stdDev;
            results[j][3] = range;
            results[j][4] = variance;
            results[j][5] = n;
            results[j][6] = coefVar;
            results[j][7] = confInterval[0]; 
            results[j][8] = confInterval[1]; 
            results[j][9] = max;
            results[j][10] = min;
        }
        return results;
    }

    public double[][] calculateCovariances(double[][] data) {
        int numCols = data[0].length;
        double[][] covarianceMatrix = new double[numCols][numCols];
        Covariance cov = new Covariance();
        
        for (int i = 0; i < numCols; i++) {
            for (int j = 0; j < numCols; j++) {
                covarianceMatrix[i][j] = cov.covariance(getColumn(data, i), getColumn(data, j));
            }
        }
        return covarianceMatrix;
    }

    private double[] getColumn(double[][] data, int colIndex) {
        int numRows = data.length;
        double[] column = new double[numRows];
        for (int i = 0; i < numRows; i++) {
            column[i] = data[i][colIndex];
        }
        return column;
    }
    
    private double[] getConfidenceInterval(double mean, double stdDev, int n) {
        double alpha = 0.05;
        TDistribution tDist = new TDistribution(n - 1);
        double tValue = tDist.inverseCumulativeProbability(1 - alpha / 2);
        double marginOfError = tValue * stdDev / Math.sqrt(n);
        
        double[] bounds = new double[2];
        bounds[0] = mean - marginOfError;
        bounds[1] = mean + marginOfError;
        return bounds;
    }



}
