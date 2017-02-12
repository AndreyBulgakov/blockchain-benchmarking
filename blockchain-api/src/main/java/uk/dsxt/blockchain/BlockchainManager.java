/*
 * *****************************************************************************
 *  * Blockchain benchmarking framework                                          *
 *  * Copyright (C) 2016 DSX Technologies Limited.                               *
 *  * *
 *  * This program is free software: you can redistribute it and/or modify       *
 *  * it under the terms of the GNU General Public License as published by       *
 *  * the Free Software Foundation, either version 3 of the License, or          *
 *  * (at your option) any later version.                                        *
 *  * *
 *  * This program is distributed in the hope that it will be useful,            *
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                       *
 *  * See the GNU General Public License for more details.                       *
 *  * *
 *  * You should have received a copy of the GNU General Public License          *
 *  * along with this program.  If not, see <http://www.gnu.org/licenses/>.      *
 *  * *
 *  * Removal or modification of this copyright notice is prohibited.            *
 *  * *
 *  *****************************************************************************
 */

package uk.dsxt.blockchain;

import lombok.extern.log4j.Log4j2;
import uk.dsxt.bitcoin.BitcoinManager;
import uk.dsxt.datamodel.blockchain.BlockchainBlock;
import uk.dsxt.datamodel.blockchain.BlockchainChainInfo;
import uk.dsxt.datamodel.blockchain.BlockchainPeer;
import uk.dsxt.ethereum.EthereumManager;
import uk.dsxt.fabric.FabricManager;

import java.io.IOException;
import java.util.List;

@Log4j2
public class BlockchainManager implements Manager {

    private String blockchainType;
    private String url;

    private Manager manager;

    public BlockchainManager(String blockchainType, String url) {
        this.blockchainType = blockchainType;
        this.url = url;
        switch (blockchainType) {
            case "fabric":
                manager = new FabricManager(url);
                break;
            case "bitcoin":
                manager = new BitcoinManager(url);
                break;
            case "ethereum":
                manager = new EthereumManager(url);
                break;
            default:
                log.error(String.format("%s blockchain currently not supported or not exist. Currently supported: " +
                        "fabric, bitcoin, ethereum", blockchainType));
                break;
        }
    }

    @Override
    public String sendMessage(byte[] body) {
        if (manager != null)
            return manager.sendMessage(body);

        return null;
    }

    @Override
    public List<Message> getNewMessages() {
        if (manager != null)
            return manager.getNewMessages();

        return null;
    }

    @Override
    public BlockchainBlock getBlock(long id) throws IOException {
        if (manager != null)
            return manager.getBlock(id);

        return null;
    }

    @Override
    public BlockchainPeer[] getPeers() throws IOException {
        if (manager != null)
            return manager.getPeers();

        return null;
    }

    @Override
    public BlockchainChainInfo getChain() throws IOException {
        if (manager != null)
            return manager.getChain();

        return null;
    }
}
