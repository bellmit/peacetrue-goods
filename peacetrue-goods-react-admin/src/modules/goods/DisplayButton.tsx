import * as React from "react";
import {Button, ButtonProps, Identifier, useNotify, useRefresh, useTranslate, useUpdate} from "react-admin";
import BuildIcon from "@material-ui/icons/Build";

export const DisplayButton = (props: ButtonProps) => {
  const {resource, record} = props;
  let notify = useNotify(), refresh = useRefresh(), translate = useTranslate();
  let url = `${resource}/${record?.id}/display/${record?.display ? 0 : 1}`;
  const [update, {loading, loaded, error}] = useUpdate(url, record?.id as Identifier);
  React.useEffect(() => {
    if (error) notify(error.message, 'error', false, false, 10 * 1000);
    if (loaded) refresh();
  }, [error, loaded, notify, refresh])
  const onClick = (e: any) => e.stopPropagation() || update();
  return (
    <Button label={translate(`ra.action.${record?.display ? 'off_sale' : 'on_sale'}`)}
            disabled={loading} onClick={onClick}>
      <BuildIcon/>
    </Button>
  )
}

