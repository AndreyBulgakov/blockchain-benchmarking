/******************************************************************************
 * Blockchain benchmarking framework                                          *
 * Copyright (C) 2017 DSX Technologies Limited.                               *
 * *
 * This program is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 * *
 * This program is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                       *
 * See the GNU General Public License for more details.                       *
 * *
 * You should have received a copy of the GNU General Public License          *
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.      *
 * *
 * Removal or modification of this copyright notice is prohibited.            *
 * *
 ******************************************************************************/

package uk.dsxt.bb.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlockInfo {

    private long blockId;
    private List<TransactionInfo> transactions;
    private long parentBlockId;
    private List<DistributionTime> distributionTimes;
    private long distributionTime95;
    private long distributionTime100;
    private long verificationTime;
    private int size;
    private long creationTime;


    public BlockInfo(long blockId, List<DistributionTime> distributionTimes) {
        this.blockId = blockId;
        this.distributionTimes = distributionTimes;
        this.transactions = new ArrayList<>();
    }

    public void calculateMaxTime() {
        List<Long> times = new ArrayList<>();
        for (DistributionTime distributionTime : distributionTimes) {
            times.add(distributionTime.getTime());
        }
        times.sort(Long::compareTo);
        distributionTime100 = times.get(times.size() - 1);
        distributionTime95 = times.get((int) (times.size() * 0.95 - 1));
    }

    public void addTransaction(TransactionInfo transaction) {
        transactions.add(transaction);
    }

    @Data
    public static class DistributionTime {
        private int nodeId;
        private long time;

        public DistributionTime(int nodeId, long time) {
            this.nodeId = nodeId;
            this.time = time;
        }
    }
}