import { useEffect, useState } from "react";
import { useParams, useSearchParams } from "react-router-dom";
import api from "../api";
import Table from "react-bootstrap/Table";
import Dropdown from "react-bootstrap/Dropdown";
import Button from "react-bootstrap/Button";
import RouteCard, { RouteInfo } from "./RouteCard";

type RouteEditorParams = {
    id: string;
};

interface RouteTrailInfo {
    ordinal: number;
    id: number;
    startPointName: string;
    startPointType: string;
    startPointAltitude: number;
    endPointName: string;
    endPointType: string;
    endPointAltitude: number;
    color: string;
    gotPoints: number;
}

interface TrailMarkingProps {
    color: string;
    width: string;
}

interface TrailInfoProps {
    trail: RouteTrailInfo;
    compact: boolean;
}

interface PointInfoProps {
    name: string;
    type: string;
    altitude: number;
}

export default function RouteViewer() {
    const { id } = useParams<RouteEditorParams>();

    const [routeInfo, setRouteInfo] = useState<RouteInfo>({ id: 0, name: "", username: "", timeModified: "", totalGotPoints: 0 });
    const [trailList, setTrailList] = useState<RouteTrailInfo[]>([]);
    const [appendableList, setAppendableList] = useState<RouteTrailInfo[]>([]);
    const [prependableList, setPrependableList] = useState<RouteTrailInfo[]>([]);

    const [searchParams] = useSearchParams();
    const editable = searchParams.get('edit') === 'true';

    const refresh = () => {
        api.get(`/routes/${id}/info`)
            .then((response) => setRouteInfo(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));

        api.get(`/routes/${id}`)
            .then((response) => setTrailList(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));

        api.get(`/routes/${id}/appendable`)
            .then((response) => setAppendableList(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));

        api.get(`/routes/${id}/prependable`)
            .then((response) => setPrependableList(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));
    };

    useEffect(refresh, []);

    const TrailMarking = ({ color, width }: TrailMarkingProps) => {
        return <svg
            viewBox="0 0 5 3"
            xmlns="http://www.w3.org/2000/svg"
            style={{ width: width }}
        >
            <rect x="0" y="0" width="5" height="1" fill="white" />
            <rect x="0" y="1" width="5" height="1" fill={color} />
            <rect x="0" y="2" width="5" height="1" fill="white" />
        </svg>
    }

    const PointInfo = ({ name, type, altitude }: PointInfoProps) => {
        return <p>
            <span className="fw-bold">{name}</span>
            <span className="text-secondary mx-1">{type}</span>
            <span className="text-muted">{altitude + " m"}</span>
        </p>
    }

    const TrailInfo = ({ trail, compact }: TrailInfoProps) => {
        return <tr>
            {!compact && <td>{trail.ordinal}</td>}
            <td>
                <PointInfo name={trail.startPointName} type={trail.startPointType} altitude={trail.startPointAltitude} />
            </td>
            {compact && <td><p className="mx-3">→</p></td>}
            <td>
                <PointInfo name={trail.endPointName} type={trail.endPointType} altitude={trail.endPointAltitude} />
            </td>
            <td><p className={compact ? "mx-3" : undefined}><TrailMarking color={trail.color} width={compact ? "32px" : "64px"} /></p></td>
            <td><p>{trail.gotPoints}</p></td>
        </tr>
    }

    const append = (trailId: number) => {
        api.post(`/routes/${id}/append`, trailId)
            .then(response => {
                console.log(response);
                refresh();
            })
            .catch(e => alert(e + "\n" + e.response?.data));
    }

    const popBack = () => {
        api.delete(`/routes/${id}/pop_back`)
            .then(response => {
                console.log(response);
                refresh();
            })
            .catch(e => alert(e + "\n" + e.response?.data));
    }

    const prepend = (trailId: number) => {
        api.post(`/routes/${id}/prepend`, trailId)
            .then(response => {
                console.log(response);
                refresh();
            })
            .catch(e => alert(e + "\n" + e.response?.data));
    }

    const popFront = () => {
        api.delete(`/routes/${id}/pop_front`)
            .then(response => {
                console.log(response);
                refresh();
            })
            .catch(e => alert(e + "\n" + e.response?.data));
    }

    return <div className="w-50 mx-auto" style={{ minWidth: "1000px" }}>
        <div className="my-2">
            <RouteCard info={routeInfo} view={false} edit={false} />
        </div>
        <Table>
            <thead>
                <tr>
                    <td>Ordinal</td>
                    <td>From</td>
                    <td>To</td>
                    <td>Color</td>
                    <td>GOT points</td>
                </tr>
            </thead>
            <tbody>
                {editable ?
                    <tr>
                        <td></td>
                        <td>
                            <Dropdown>
                                <Dropdown.Toggle variant="success">
                                    Prepend
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    {prependableList.map((trail, id) => (
                                        <Dropdown.Item key={id} onClick={() => prepend(trail.id)}>
                                            <table>
                                                <tbody>
                                                    <TrailInfo trail={trail} compact={true} />
                                                </tbody>
                                            </table>
                                        </Dropdown.Item>
                                    ))}
                                </Dropdown.Menu>
                            </Dropdown>
                        </td>
                        <td>
                            <Button
                                variant="danger"
                                onClick={popFront}>Pop front</Button>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    : null}
                {trailList.map((trail, id) => <TrailInfo key={id} trail={trail} compact={false} />)}
                {editable ?
                    <tr>
                        <td></td>
                        <td>
                            <Dropdown>
                                <Dropdown.Toggle variant="success">
                                    Append
                                </Dropdown.Toggle>
                                <Dropdown.Menu>
                                    {appendableList.map((trail, id) => (
                                        <Dropdown.Item key={id} onClick={() => append(trail.id)}>
                                            <table>
                                                <tbody>
                                                    <TrailInfo trail={trail} compact={true} />
                                                </tbody>
                                            </table>
                                        </Dropdown.Item>
                                    ))}
                                </Dropdown.Menu>
                            </Dropdown>
                        </td>
                        <td>
                            <Button
                                variant="danger"
                                onClick={popBack}>Pop back</Button>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    : null}
            </tbody>
        </Table>
    </div>
}