package org.baoshichain.guessgame.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * Auto generated code.<br>
 * <strong>Do not modify!</strong><br>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or {@link org.web3j.codegen.SolidityFunctionWrapperGenerator} to update.
 *
 * <p>Generated with web3j version 2.3.0.
 */
public final class Game extends Contract {
    private static final String BINARY = "606060405260008060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550341561005057600080fd5b5b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b5b61187d806100a26000396000f300606060405236156100a2576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806330852db8146100a757806357e871e71461011c57806364c926ef146101455780636d9236f314610192578063730b8381146101f55780638da5cb5b14610239578063995e43391461028e578063b85cd941146102c9578063de40123c14610309578063fd48a1a514610340575b600080fd5b34156100b257600080fd5b6100da600480803590602001909190803590602001909190803590602001909190505061037e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561012757600080fd5b61012f610514565b6040518082815260200191505060405180910390f35b341561015057600080fd5b610178600480803590602001909190803590602001909190803590602001909190505061051d565b604051808215151515815260200191505060405180910390f35b341561019d57600080fd5b6101b360048080359060200190919050506106ad565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561020057600080fd5b61021f60048080359060200190919080359060200190919050506106e0565b604051808215151515815260200191505060405180910390f35b341561024457600080fd5b61024c61085e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561029957600080fd5b6102af6004808035906020019091905050610883565b604051808215151515815260200191505060405180910390f35b34156102d457600080fd5b6102f36004808035906020019091908035906020019091905050610ba8565b6040518082815260200191505060405180910390f35b341561031457600080fd5b61032a6004808035906020019091905050610c7d565b6040518082815260200191505060405180910390f35b341561034b57600080fd5b6103616004808035906020019091905050610c95565b604051808381526020018281526020019250505060405180910390f35b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156103dc57600080fd5b308585856103e8610de9565b808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001848152602001838152602001828152602001945050505050604051809103906000f080151561044957600080fd5b9050806001600087815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055507f8473dc93a9be53aced870a3d621ce5fdd05a6617c328e0692915515381587a768582604051808381526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019250505060405180910390a18091505b509392505050565b60004390505b90565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561057b57600080fd5b6001600086815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663b60b9fcb85856000604051602001526040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018083815260200182815260200192505050602060405180830381600087803b151561063057600080fd5b6102c65a03f1151561064157600080fd5b50505060405180519050156106a0577f43ad12bfccf2e9cbe47203b653c50eaa70dac1467cdf47b743ba3fbc3c30ecf185858560405180848152602001838152602001828152602001935050505060405180910390a1600191506106a5565b600091505b509392505050565b60016020528060005260406000206000915054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561073e57600080fd5b6001600085815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050600115158173ffffffffffffffffffffffffffffffffffffffff166395805dad856000604051602001526040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b15156107ef57600080fd5b6102c65a03f1151561080057600080fd5b5050506040518051905015151415610852577f4a8a0875ee297c5ebddd86b9804aaad183edac07a542068880347ff49b2243ef846040518082815260200191505060405180910390a160019150610857565b600091505b5092915050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000806000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156108e157600080fd5b6001600084815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663653721476000604051602001526040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b151561098357600080fd5b6102c65a03f1151561099457600080fd5b5050506040518051905015610b9d578073ffffffffffffffffffffffffffffffffffffffff16638c3f709c6000604051602001526040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1515610a0f57600080fd5b6102c65a03f11515610a2057600080fd5b5050506040518051905060026000858152602001908152602001600020819055507fe88a161aba809980877c2912e59303bd9e87c951aa644625af64738ad4ca44a7838273ffffffffffffffffffffffffffffffffffffffff16638c3f709c6000604051602001526040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1515610acf57600080fd5b6102c65a03f11515610ae057600080fd5b505050604051805190508373ffffffffffffffffffffffffffffffffffffffff16632ef796af6000604051602001526040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1515610b5657600080fd5b6102c65a03f11515610b6757600080fd5b5050506040518051905060405180848152602001838152602001828152602001935050505060405180910390a160019150610ba2565b600091505b50919050565b6000806001600085815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff166350b44712846000604051602001526040518263ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180828152602001915050602060405180830381600087803b1515610c5857600080fd5b6102c65a03f11515610c6957600080fd5b5050506040518051905091505b5092915050565b60026020528060005260406000206000915090505481565b60008060006001600085815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff166348cd4cb16000604051602001526040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1515610d3c57600080fd5b6102c65a03f11515610d4d57600080fd5b505050604051805190508173ffffffffffffffffffffffffffffffffffffffff1663083c63236000604051602001526040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1515610dc357600080fd5b6102c65a03f11515610dd457600080fd5b50505060405180519050925092505b50915091565b604051610a5880610dfa833901905600606060405260008060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600155600060025560006003556000600455600060055560006006556000600755600060085560006009556000600a556000600b60006101000a81548160ff0219169083151502179055506000600b60016101000a81548160ff0219169083151502179055506001600b60026101000a81548160ff0219169083151502179055506000600e5534156100d857600080fd5b604051608080610a58833981016040528080519060200190919080519060200190919080519060200190919080519060200190919050505b836000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550816003819055508060048190555060035460058190555082600a819055505b505050505b6108d5806101836000396000f3006060604052361561011b576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063083c6323146101205780631f2698ab146101495780632ef796af1461017657806348cd4cb11461019f57806350b44712146101c857806357e871e7146101ff578063648685101461022857806365372147146102515780636af59a8e1461027e5780637eaef50c146102a75780638c3f709c146102d45780638da5cb5b146102fd57806395805dad146103525780639a8f716d1461038d578063b1bb5855146103c4578063b60b9fcb146103ed578063d7c81b5514610431578063eda886e81461045a578063f5eef45414610487578063f6a30e3e146104b0578063f9f0164a146104d9575b600080fd5b341561012b57600080fd5b610133610502565b6040518082815260200191505060405180910390f35b341561015457600080fd5b61015c610508565b604051808215151515815260200191505060405180910390f35b341561018157600080fd5b61018961051b565b6040518082815260200191505060405180910390f35b34156101aa57600080fd5b6101b2610521565b6040518082815260200191505060405180910390f35b34156101d357600080fd5b6101e96004808035906020019091905050610527565b6040518082815260200191505060405180910390f35b341561020a57600080fd5b61021261053f565b6040518082815260200191505060405180910390f35b341561023357600080fd5b61023b610548565b6040518082815260200191505060405180910390f35b341561025c57600080fd5b610264610551565b604051808215151515815260200191505060405180910390f35b341561028957600080fd5b6102916106c0565b6040518082815260200191505060405180910390f35b34156102b257600080fd5b6102ba6106c6565b604051808215151515815260200191505060405180910390f35b34156102df57600080fd5b6102e76106d9565b6040518082815260200191505060405180910390f35b341561030857600080fd5b6103106106df565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561035d57600080fd5b6103736004808035906020019091905050610704565b604051808215151515815260200191505060405180910390f35b341561039857600080fd5b6103ae6004808035906020019091905050610798565b6040518082815260200191505060405180910390f35b34156103cf57600080fd5b6103d76107b0565b6040518082815260200191505060405180910390f35b34156103f857600080fd5b61041760048080359060200190919080359060200190919050506107b6565b604051808215151515815260200191505060405180910390f35b341561043c57600080fd5b61044461087e565b6040518082815260200191505060405180910390f35b341561046557600080fd5b61046d610884565b604051808215151515815260200191505060405180910390f35b341561049257600080fd5b61049a610897565b6040518082815260200191505060405180910390f35b34156104bb57600080fd5b6104c361089d565b6040518082815260200191505060405180910390f35b34156104e457600080fd5b6104ec6108a3565b6040518082815260200191505060405180910390f35b60095481565b600b60019054906101000a900460ff1681565b60025481565b60085481565b600c6020528060005260406000206000915090505481565b60004390505b90565b60004390505b90565b60008060001515600b60009054906101000a900460ff16151514151561057657600080fd5b60016009540143101561058857600080fd5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156105e357600080fd5b6004546006541015610641576001600b60006101000a81548160ff0219169083151502179055506000600b60016101000a81548160ff0219169083151502179055506000600b60026101000a81548160ff0219169083151502179055505b6001600954014060019004905060016006548281151561065d57fe5b0601600281905550600c60006002548152602001908152602001600020546001819055506001600b60006101000a81548160ff0219169083151502179055506000600b60016101000a81548160ff021916908315150217905550600191505b5090565b60075481565b600b60009054906101000a900460ff1681565b60015481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561076157600080fd5b4360088190555060085482016009819055506001600b60016101000a81548160ff021916908315150217905550600190505b919050565b600d6020528060005260406000206000915090505481565b60035481565b60008060001515600b60019054906101000a900460ff16151514156107da57600080fd5b6009544311156107e957600080fd5b6005548311156107f857600080fd5b600090505b6001830381101561084157600160066000828254019250508190555083600c60006006548152602001908152602001600020819055505b80806001019150506107fd565b82600d60008681526020019081526020016000206000828254019250508190555082600560008282540392505081905550600191505b5092915050565b600a5481565b600b60029054906101000a900460ff1681565b60055481565b60045481565b600654815600a165627a7a723058205b9a74ebdb07a18a1394cb5884ced2fa3470f13ba8fe4dcd056de4d9dcd820790029a165627a7a7230582092e9f5278c1d8bcb8b62ba08e850d7af767a5553d3643f005d2f2f8d8b8b512c0029\r\n";

    private Game(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    private Game(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<AddGameLogEventResponse> getAddGameLogEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("AddGameLog", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<AddGameLogEventResponse> responses = new ArrayList<AddGameLogEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            AddGameLogEventResponse typedResponse = new AddGameLogEventResponse();
            typedResponse.gameId = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.gameAddress = (Address) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AddGameLogEventResponse> addGameLogEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("AddGameLog", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, AddGameLogEventResponse>() {
            @Override
            public AddGameLogEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                AddGameLogEventResponse typedResponse = new AddGameLogEventResponse();
                typedResponse.gameId = (Uint256) eventValues.getNonIndexedValues().get(0);
                typedResponse.gameAddress = (Address) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public List<JoinGameLogEventResponse> getJoinGameLogEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("JoinGameLog", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<JoinGameLogEventResponse> responses = new ArrayList<JoinGameLogEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            JoinGameLogEventResponse typedResponse = new JoinGameLogEventResponse();
            typedResponse.gameId = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.participantId = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.tokenNumber = (Uint256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<JoinGameLogEventResponse> joinGameLogEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("JoinGameLog", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, JoinGameLogEventResponse>() {
            @Override
            public JoinGameLogEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                JoinGameLogEventResponse typedResponse = new JoinGameLogEventResponse();
                typedResponse.gameId = (Uint256) eventValues.getNonIndexedValues().get(0);
                typedResponse.participantId = (Uint256) eventValues.getNonIndexedValues().get(1);
                typedResponse.tokenNumber = (Uint256) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public List<StartGameLogEventResponse> getStartGameLogEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("StartGameLog", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<StartGameLogEventResponse> responses = new ArrayList<StartGameLogEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            StartGameLogEventResponse typedResponse = new StartGameLogEventResponse();
            typedResponse.gameid = (Uint256) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<StartGameLogEventResponse> startGameLogEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("StartGameLog", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, StartGameLogEventResponse>() {
            @Override
            public StartGameLogEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                StartGameLogEventResponse typedResponse = new StartGameLogEventResponse();
                typedResponse.gameid = (Uint256) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public List<RewardLogEventResponse> getRewardLogEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("RewardLog", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<RewardLogEventResponse> responses = new ArrayList<RewardLogEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            RewardLogEventResponse typedResponse = new RewardLogEventResponse();
            typedResponse.gameId = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.participantId = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.rewardNum = (Uint256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RewardLogEventResponse> rewardLogEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("RewardLog", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, RewardLogEventResponse>() {
            @Override
            public RewardLogEventResponse call(Log log) {
                EventValues eventValues = extractEventParameters(event, log);
                RewardLogEventResponse typedResponse = new RewardLogEventResponse();
                typedResponse.gameId = (Uint256) eventValues.getNonIndexedValues().get(0);
                typedResponse.participantId = (Uint256) eventValues.getNonIndexedValues().get(1);
                typedResponse.rewardNum = (Uint256) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public Future<TransactionReceipt> newGame(Uint256 gameId, Uint256 maxTicketInput, Uint256 minTicketInput) {
        Function function = new Function("newGame", Arrays.<Type>asList(gameId, maxTicketInput, minTicketInput), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> blockNumber() {
        Function function = new Function("blockNumber", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> joinGame(Uint256 gameId, Uint256 participantId, Uint256 tokenNumber) {
        Function function = new Function("joinGame", Arrays.<Type>asList(gameId, participantId, tokenNumber), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Address> gameMap(Uint256 param0) {
        Function function = new Function("gameMap", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> startGame(Uint256 gameId, Uint256 blockNumber) {
        Function function = new Function("startGame", Arrays.<Type>asList(gameId, blockNumber), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Address> owner() {
        Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<TransactionReceipt> getResult(Uint256 gameId) {
        Function function = new Function("getResult", Arrays.<Type>asList(gameId), Collections.<TypeReference<?>>emptyList());
        return executeTransactionAsync(function);
    }

    public Future<Uint256> ticketInfo(Uint256 gameId, Uint256 ticketId) {
        Function function = new Function("ticketInfo", 
                Arrays.<Type>asList(gameId, ticketId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<Uint256> gameRewards(Uint256 param0) {
        Function function = new Function("gameRewards", 
                Arrays.<Type>asList(param0), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturnAsync(function);
    }

    public Future<List<Type>> gameTimeInfo(Uint256 gameId) {
        Function function = new Function("gameTimeInfo", 
                Arrays.<Type>asList(gameId), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return executeCallMultipleValueReturnAsync(function);
    }

    public static Future<Game> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Game.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Future<Game> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployAsync(Game.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static Game load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Game(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Game load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Game(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class AddGameLogEventResponse {
        public Uint256 gameId;

        public Address gameAddress;
    }

    public static class JoinGameLogEventResponse {
        public Uint256 gameId;

        public Uint256 participantId;

        public Uint256 tokenNumber;
    }

    public static class StartGameLogEventResponse {
        public Uint256 gameid;
    }

    public static class RewardLogEventResponse {
        public Uint256 gameId;

        public Uint256 participantId;

        public Uint256 rewardNum;
    }
}
