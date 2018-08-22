package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String[] in = br.readLine().split(" ");
            int p1 = Integer.parseInt(in[0]);
            int p2 = Integer.parseInt(in[1]);
            int t=0;

            Data data1 = new Data();
            data1.pos1 = p1;
            data1.pos2 = p2;
            data1.diff = getDiff(p2, p1);
            data1.force = getForce(data1.diff, data1.pos1, data1.pos2);
            data1.a1 = getAcceleration(data1.force, p1);
            data1.a2 = getAcceleration(data1.force, p2);
            data1.t = 1;
            data1.u1 = 0;
            data1.u2 = 0;
            data1.dist1 = getDistance(data1.u1, data1.t, data1.a1);
            data1.dist2 = getDistance(data1.u2, data1.t, data1.a2);
            data1.v1 = getFinalVelocity(data1.u1, data1.a1, data1.t);
            data1.v2 = getFinalVelocity(data1.u2, data1.a2, data1.t);

            Data data2 = new Data();
            data2.pos1 = ((double)data1.pos1 + data1.dist1);
            data2.pos2 = ((double)data1.pos2 - data1.dist2);
            data2.diff = getDiff(data2.pos1,data2.pos2);
            data2.force = getForce(data2.diff,data2.pos1,data2.pos2);
            data2.a1 = getAcceleration(data2.force,data2.pos1);
            data2.a2 = getAcceleration(data2.force,data2.pos2);
            data2.t = data1.t + 1;
            data2.u1 = data1.v1;
            data2.u2 = data1.v2;
            data2.dist1 = getDistance(data2.u1,data2.t-data1.t,data2.a1);
            data2.dist2 = getDistance(data2.u2,data2.t-data1.t,data2.a2);
            data2.v1 = getFinalVelocity(data2.u1,data2.a1,(data2.t-data1.t));
            data2.v2 = getFinalVelocity(data2.u2,data2.a2,(data2.t-data1.t));


            while (data2.pos1 < data2.pos2) {
                data1.setData(data2);

                data2.pos1 = ((double)data1.pos1 + data1.dist1);
                data2.pos2 = ((double)data1.pos2 - data1.dist2);
                data2.diff = getDiff(data2.pos1,data2.pos2);
                data2.force = getForce(data2.diff,data2.pos1,data2.pos2);
                data2.a1 = getAcceleration(data2.force,data2.pos1);
                data2.a2 = getAcceleration(data2.force,data2.pos2);
                data2.t = data1.t + 1;
                data2.u1 = data1.v1;
                data2.u2 = data1.v2;
                data2.dist1 = getDistance(data2.u1,data2.t-data1.t,data2.a1);
                data2.dist2 = getDistance(data2.u2,data2.t-data1.t,data2.a2);
                data2.v1 = getFinalVelocity(data2.u1,data2.a1,(data2.t-data1.t));
                data2.v2 = getFinalVelocity(data2.u2,data2.a2,(data2.t-data1.t));

            }

            double avg = (data1.pos1 + data1.pos2 + data2.pos1 + data2.pos2) / (double) 4;

            if (avg > (double)data1.pos2) {
                double divider = 0.5;
                while (avg > data1.pos2) {

                    data1.dist1 = getDistance(data1.u1,divider,data1.a1);
                    data1.dist2 = getDistance(data1.u2,divider,data1.a2);
                    data1.v1 = getFinalVelocity(data1.u1,data1.a1,divider);
                    data1.v2 = getFinalVelocity(data1.u2,data1.a2,divider);

                    data2.pos1 = (int)Math.round(((double)data1.pos1 + data1.dist1));
                    data2.pos2 = (int)Math.round(((double)data1.pos2 - data1.dist2));
                    data2.diff = Math.round(getDiff(data2.pos1,data2.pos2));
                    data2.force = getForce(data2.diff,data2.pos1,data2.pos2);
                    data2.a1 = getAcceleration(data2.force,data2.pos1);
                    data1.a2 = getAcceleration(data2.force,data2.pos2);
                    data2.t = data1.t + divider;
                    data2.u1 = data1.v1;
                    data2.u2 = data1.v2;
                    data2.dist1 = getDistance(data2.u1,data2.t-data1.t,data2.a1);
                    data2.dist2 = getDistance(data2.u2,data2.t-data1.t,data2.a2);
                    data2.v1 = getFinalVelocity(data2.u1,data2.a1,(data2.t-data1.t));
                    data2.v2 = getFinalVelocity(data2.u2,data2.a2,(data2.t-data1.t));

                    avg = (data1.pos1 + data1.pos2 + data2.pos1 + data2.pos2) / (double) 4;
                    if (avg > data1.pos2) {
                        divider = divider / 2;
                    }
                }
            }
            System.out.println(avg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getDiff(double pos1, double pos2) {
        return (pos2 - pos1);
    }

    public static double getForce(double diff, double pos1, double pos2) {
        return ((10 * pos1 * pos2) / (double) (diff * diff));
    }

    public static double getAcceleration(double force, double pos) {
        return (force/(double) pos);
    }

    public static double getDistance(double u, double t, double a) {
        return (u * t) + (0.5 * (a * (t * t)));
    }

    public static double getFinalVelocity(double u, double a, double t) {
        return (u + (a * t));
    }
}

class Data {

    public void setData(Data data) {
        pos1 = data.pos1;
        pos2 = data.pos2;
        diff = data.diff;
        t = data.t;
        force = data.force;
        a1 = data.a1;
        a2 = data.a2;
        dist1 = data.dist1;
        dist2 = data.dist2;
        u1 = data.u1;
        u2 = data.u2;
        v1 = data.v1;
        v2 = data.v2;
    }

    double t, force, a1, a2, dist1, dist2, u1, u2, v1, v2,pos1,pos2,diff;
}